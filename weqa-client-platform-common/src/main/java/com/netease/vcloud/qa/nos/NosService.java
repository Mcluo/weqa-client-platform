package com.netease.vcloud.qa.nos;

import com.netease.cloud.ClientException;
import com.netease.cloud.ServiceException;
import com.netease.cloud.auth.BasicCredentials;
import com.netease.cloud.auth.Credentials;
import com.netease.cloud.services.nos.NosClient;
import com.netease.cloud.services.nos.model.GeneratePresignedUrlRequest;
import com.netease.cloud.services.nos.model.NOSObject;
import com.netease.cloud.services.nos.model.PutObjectResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;


/**
 * Created by luqiuwei@corp.netease.com
 * on 2022-12-27 19:24:46
 * NOS存储服务
 */
@Service
public class NosService {

    private static final Logger NOS_LOGGER = LoggerFactory.getLogger("CommonLog");

    private static final int SUCCESS_CODE = 0 ;

    private static final long ONE_HOUR = 60 * 60 * 1000L ;

    private String G2_AUTO_LOG_PATH = "/g2-log/" ;

    private String accessKey = "ab1856bb39044591939d7b94e1b8e5ee" ;//"33b4f5928df44d94a03f8be77ef3ffd1" ;

    private String secretaryKey = "ed1573cd7de34086a0ba5c3c521c6df1" ;//"25ccc58f586e4f498c8883203d609cbb" ;

    private String bucketName = "jdvodrviigh4f" ;//"haitao-sec-storage-permanent" ;


    //    private static final String endPoint = "nos-eastchina1.126.net" ;//目前有效的endpoint nos-eastchina1.126.net
    private String endPoint = "nos-muljd.163yun.com";// "nos.netease.com" ;

    private static final String DOWN_PROTOCOL = "http://" ;

    /**
     * URL的默认有效时间,1小时
     */
    private long defaultUrlTime = ONE_HOUR ;

    private NosClient nosClient ;

//    private PropertiesConfig propertiesConfig ;

    /**
     *
     */
    @PostConstruct
    public void init(){
        Credentials credentials = new BasicCredentials(accessKey, secretaryKey);
        nosClient = new NosClient(credentials);
        nosClient.setEndpoint(endPoint);
        if (!nosClient.doesBucketExist(bucketName)){
            NOS_LOGGER.info("[NosService.init]nosBucket not exist");
            try {
                nosClient.createBucket(bucketName);
            }catch (ServiceException e){
                NOS_LOGGER.error("[NosService.init] create bucket service exception",e);
            }catch (ClientException e){
                NOS_LOGGER.error("[NosService.init] create bucket client exception",e);
            }
        }
    }

    @PreDestroy
    public void destroy() {
        if (nosClient != null) {
            try {
                nosClient.shutdown();
            } catch (ServiceException e) {
                NOS_LOGGER.error("[NosService.destroy] create bucket service exception", e);
            } catch (ClientException e) {
                NOS_LOGGER.error("[NosService.destroy] create bucket client exception", e);
            }
        }
    }

    /**
     * 上传一个文件(暂时采用流式上传模式)
     * @param filePath
     * @param inputStream
     * @return
     */
    public String uploadFile(String filePath , InputStream inputStream) {
        String fileName = null;
        if (nosClient != null) {
            PutObjectResult putObjectResult = null;
            try {
                fileName = this.getNosPath(filePath);
                putObjectResult = nosClient.putObject(bucketName, fileName, inputStream, null);
            } catch (ServiceException e) {
                NOS_LOGGER.error("[NosFileManager.uploadFile] create bucket service exception", e);
            } catch (ClientException e) {
                NOS_LOGGER.error("[NosFileManager.uploadFile] create bucket client exception", e);
            }
            if (putObjectResult == null || putObjectResult.getCallbackRetCode() != SUCCESS_CODE) {
                //上传和失败，则返回的fileName为空
                fileName = null ;
            }
        }
        return fileName;
    }

    /**
     * 删除NOS上的远程文件
     * @return
     */
    public boolean deleteFile(String filePath){
        if (StringUtils.isBlank(filePath)){
            NOS_LOGGER.error("[NosFileManager.deleteFile] delete file path is blank");
            return false ;
        }
        boolean flag = false ;
        if (nosClient!=null){
            PutObjectResult putObjectResult = null ;
            try {
//                String nosPath = this.getNosPath(filePath);
                nosClient.deleteObject(bucketName,filePath);
                flag = true ;
            }catch (ServiceException e){
                NOS_LOGGER.error("[NosFileManager.deleteFile] create bucket service exception",e);
            }catch (ClientException e){
                NOS_LOGGER.error("[NosFileManager.deleteFile] create bucket client exception",e);
            }
        }
        return flag ;
    }


    public InputStream getFile(String filePath) {
        if (StringUtils.isBlank(filePath)){
            NOS_LOGGER.error("[NosFileManager.deleteFile] delete file path is blank");
            return null;
        }
        InputStream inputStream = null ;
        if (nosClient != null) {
            NOSObject nosObject = null ;
            try {
                nosObject = nosClient.getObject(bucketName, filePath);
            } catch (ServiceException e) {
                NOS_LOGGER.error("[NosFileManager.uploadFile] create bucket service exception", e);
            } catch (ClientException e) {
                NOS_LOGGER.error("[NosFileManager.uploadFile] create bucket client exception", e);
            }
            if (nosObject ==null){
                return null;
            }
            inputStream = nosObject.getObjectContent()  ;
        }
        return inputStream ;
    }

    private String getNosPath(String filePath){
        String  nosPath = null ;
        if (StringUtils.isNotBlank(filePath)) {
            //如果存在,就使用具体文件路径
            nosPath = G2_AUTO_LOG_PATH + filePath;
        }else {
            //如果传入不存在,就是只能存毫秒值了
            nosPath = G2_AUTO_LOG_PATH + System.currentTimeMillis() ;
        }
        return nosPath ;
    }

    public String getDownFileUrl(String filePath){
        return this.getDownFileUrl(filePath,defaultUrlTime) ;
    }


    public String getDownFileUrl(String filePath,long urlTime){
        return this.getPrivateUri(bucketName,filePath, urlTime);
    }
    /**
     *
     * @param bucketName 分桶名字
     * @param key 对象object
     * @param time 生效时间
     * @return
     */
    private String getPrivateUri(String bucketName, String key, long time) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key);
        generatePresignedUrlRequest.setExpiration(new Date(System.currentTimeMillis() + time));
        URL url = nosClient.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

}
