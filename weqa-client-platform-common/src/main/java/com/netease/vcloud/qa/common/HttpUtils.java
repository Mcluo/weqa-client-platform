package com.netease.vcloud.qa.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/26 20:13
 */
public class HttpUtils {

    private static final Logger COMMON_LOGGER = LoggerFactory.getLogger("CommonLog");

    HttpClientContext context = createContext();
    CloseableHttpClient client = createClient();

    private static HttpUtils instance = null;
    private HttpUtils(){}
    public static HttpUtils getInstance(){
        if (instance == null) {
            instance = new HttpUtils();
        }
        return instance;
    }

    public JSONObject get(String url) {
        return this.get(url,getDefaultHeaders()) ;
    }

    public JSONObject get(String url,Map<String,String> headers){
        CloseableHttpResponse response = null;
        HttpGet httpGet = new HttpGet(url);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpGet.addHeader(entry.getKey(), entry.getValue());
        }
        try {
            response = client.execute(httpGet, context);
        }catch (Exception e){
            COMMON_LOGGER.error("[HttpUtils.get] send request exception",e);
        }
        return buildJSONResult(response) ;
    }

    public JSONObject formPost(String url
            , Map<String, String> data){
        return this.formPost(url,getDefaultHeaders(),data) ;
    }

    public JSONObject formPost(String url
            , Map<String, String> headers
            , Map<String, String> data){
        CloseableHttpResponse response=null;
        try {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                valuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs);
            httpPost.addHeader("Content-WorkFlowType", "application/x-www-form-urlencoded");
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
            httpPost.setEntity(entity);
            response = client.execute(httpPost, context);
        }catch (Exception e){
            COMMON_LOGGER.error("[HttpUtils.httpPost]parse response exception",e);
        }
        return buildJSONResult(response) ;
    }

    public JSONObject jsonPost(String url
            , String jsonBody) {
        return this.jsonPost(url,getDefaultHeaders(), jsonBody) ;
    }


    public JSONObject jsonPost(String url
            , Map<String, String> headers
            , String jsonBody){
        CloseableHttpResponse response=null;
        try {
            HttpPost httpPost = new HttpPost(url);

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
            StringEntity entity = new StringEntity(jsonBody, "UTF-8");//解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            httpPost.addHeader("content-type", "application/json");
            httpPost.setEntity(entity);
            response = client.execute(httpPost, context);
        }catch (Exception e){
            COMMON_LOGGER.error("[HttpUtils.httpPost]parse response exception",e);
        }
        return buildJSONResult(response) ;
    }


    private static Map<String, String> getDefaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//        headers.put("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12A365 Safari/600.1.4");
        return headers;
    }

    public static HttpClientContext createContext() {
        CookieStore cookieStore = new BasicCookieStore();
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);
        return context;
    }

    public static CloseableHttpClient createClient() {

        HttpClientConnectionManager manager = createManager();
        RequestConfig config = RequestConfig
                .custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .setMaxRedirects(10)
                .setRedirectsEnabled(true)
                .setRelativeRedirectsAllowed(true)
                .setConnectTimeout(3000)
                .setSocketTimeout(3000)
                .setConnectionRequestTimeout(3000)
                .build();
        CloseableHttpClient instance =
                HttpClientBuilder.create()
                        .setRedirectStrategy(new LaxRedirectStrategy())
                        .setDefaultRequestConfig(config)
                        .setConnectionManager(manager)
                        .build();
        return instance;
    }

    public static HttpClientConnectionManager createManager() {
        try {
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                    return true;
                }
            }).build();
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(
                    sslContext, hostnameVerifier);
            Registry registry = RegistryBuilder
                    .create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", factory).build();

            PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(registry);
            manager.setDefaultMaxPerRoute(5);
            manager.setMaxTotal(50);
            return manager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JSONObject buildJSONResult(CloseableHttpResponse response){
        if (response != null && response.getStatusLine() != null && response.getStatusLine().getStatusCode() == 200) {
            try {
                String body = EntityUtils.toString(response.getEntity());
                COMMON_LOGGER.info("[HttpRequest.buildJSONResult]response body :"+ body);
                return JSONObject.parseObject(body);
            } catch (Exception e) {
                COMMON_LOGGER.error("[HttpRequest.buildJSONResult]parse response exception",e);
            }
        }else {
            if(response==null) {
                COMMON_LOGGER.error("[HttpRequest.buildJSONResult]response is null or fail");
            }else {
                COMMON_LOGGER.error("[HttpRequest.buildJSONResult]response status is not 200,statusLine is "+response.getStatusLine());
                if (response.getStatusLine()!=null) {
                    COMMON_LOGGER.error("[HttpRequest.buildJSONResult]response status is not 200,status is " + response.getStatusLine().getStatusCode());
                }
            }
        }
        return null ;
    }
}
