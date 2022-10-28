package com.netease.vcloud.qa.service.tc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.CommonData;
import com.netease.vcloud.qa.common.HttpUtils;
import com.netease.vcloud.qa.service.tc.data.ClientTcData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 和TC平台交互的类
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/26 19:38
 */
@Service
public class TCManagerService {

    private static final Logger TC_LOGGER = LoggerFactory.getLogger("TCLog");

    private static final String TC_URL = "https://tc.hz.netease.com/api/sa/cases/cases_list" ;

    private static final String TC_TOKEN = "a56%34xeg_qe568sei2" ;

    private static final String TOKEN_ARGS = "token" ;

    private static final String PROJECT_ARGS = "projectId" ;

    private static final String START_ARGS = "casesStartDate" ;

    private static final String END_ARGS = "casesEndDate" ;

    /**
     * 查询日期格式
     */
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 最大查询时间7天
     */
    private static final Long MAX_QUERY_TIME = CommonData.ONE_DAY * 7 ;

    public List<ClientTcData> getProjectTcData(Long projectId , Long start , Long finish){
        if (projectId == null || start == null || finish == null){
            return null ;
        }
        List<ClientTcData> clientTcDataList = new ArrayList<ClientTcData>() ;
        //限制7天为一个查询周期
        while (true){
            long tempStartTime = finish - MAX_QUERY_TIME  ;
            JSONObject jsonObject = this.requestProjectTcData(projectId, start<tempStartTime ? tempStartTime : start, finish );
            if (jsonObject == null) {
                continue;
            }
            int code = jsonObject.getInteger("code");
            if (code != 200) {
                continue;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            if (jsonArray != null && jsonArray.size() > 0) {
                for (Object tcObject : jsonArray) {
                    JSONObject tcJSON = (JSONObject) tcObject;
                    ClientTcData clientTcData = new ClientTcData();
                    clientTcData.setProjectId(projectId);
                    clientTcData.setCaseId(tcJSON.getLong("id"));
                    clientTcData.setCaseName(tcJSON.getString("title"));
                    clientTcDataList.addAll(clientTcDataList);
                }
            }
            if (start > tempStartTime){
                break;
            }
            finish = tempStartTime ;
        }
        return clientTcDataList ;
    }

    public JSONObject requestProjectTcData(long projectId ,long start, long finish){
        StringBuilder baseUrlStr = new StringBuilder(TC_URL) ;
        baseUrlStr.append("?").append(PROJECT_ARGS).append("=").append(projectId) ;
        baseUrlStr.append("&").append(START_ARGS).append("=").append(simpleDateFormat.format(new Date(start))) ;
        baseUrlStr.append("&").append(END_ARGS).append("=").append(simpleDateFormat.format(new Date(finish))) ;
        Map<String,String> headerMap = new HashMap<String, String>() ;
        JSONObject result = HttpUtils.getInstance().get(baseUrlStr.toString(),headerMap) ;
        if (result == null){
            TC_LOGGER.error("[TCManagerService.requestProjectTcData] project tc data result is null");
        }
        return result ;
    }

}
