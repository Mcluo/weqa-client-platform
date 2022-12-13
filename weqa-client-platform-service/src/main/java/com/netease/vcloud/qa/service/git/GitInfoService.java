package com.netease.vcloud.qa.service.git;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.common.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/13 14:51
 */
@Service
public class GitInfoService {

    private static Logger logger = LoggerFactory.getLogger("git");

    private static final String GITLAB_PROTOCOL = "http://" ;

    private static final String GITLAB_SERVICE = "g.hz.netease.com" ;

    private static final String QUERY_PARAM_ARGS = "search" ;

    private static final String GITLAB_PROJECT_QUERY_PATH = "/api/v4/projects/%s/repository/branches" ;


    private static final String GIT_PRIVATE_TOKEN_ARGS = "PRIVATE-TOKEN" ;
    /**
     * yunxin-accurate-test
     * tokenName:codeLineStatus
     */
    private static final String GIT_PRIVATE_TOKEN = "ExFeNTt4wynT42ks7sgn" ;

    public List<String> queryGitBranchList(Long projectId, String queryKey){
        String baseUrl = GITLAB_PROTOCOL + GITLAB_SERVICE + GITLAB_PROJECT_QUERY_PATH ;
        baseUrl = String.format(baseUrl, projectId+"") ;
        String url = baseUrl +"?"+QUERY_PARAM_ARGS+"="+queryKey ;
        Map<String ,String> header = new HashMap<>() ;
        header.put(GIT_PRIVATE_TOKEN_ARGS,GIT_PRIVATE_TOKEN) ;
        JSONArray resultArray = null ;
        try {
            resultArray = HttpUtils.getInstance().getArray(url,header) ;
        }catch (Exception e){
            logger.error("[GitInfoService.queryGitBranchList] get gitBranch info exception",e);
        }
        if (resultArray==null){
            return null ;
        }
        List<String> branchList = new ArrayList<String>() ;
        for (Object object : resultArray){
            if (object instanceof JSONObject){
                JSONObject jsonObject = (JSONObject) object ;
                String branchName = jsonObject.getString("name") ;
                branchList.add(branchName) ;
            }
        }
        return branchList ;
    }
}
