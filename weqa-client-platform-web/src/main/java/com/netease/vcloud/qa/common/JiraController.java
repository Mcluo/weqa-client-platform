package com.netease.vcloud.qa.common;

import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.version.JiraService;
import com.netease.vcloud.qa.version.data.JiraVersion;
import com.netease.vcloud.qa.version.data.JiraVersionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/31 22:00
 */
@RestController
@RequestMapping("/jira/info")
public class JiraController {

    @Autowired
    private JiraService jiraService ;


    /**
     * http://127.0.0.1:8788/g2-client/jira/info/sync
     * @param jiraKey
     * @return
     */
    @RequestMapping("/sync")
    @ResponseBody
    public ResultVO asyncJiraInfo(@RequestParam(name="jiraKey",required = false,defaultValue = "NRTCG2") String jiraKey){
        List<JiraVersion> jiraVersionList = jiraService.getProjectVersions(jiraKey) ;
        ResultVO resultVO = ResultUtils.buildSuccess(jiraVersionList) ;
        return resultVO ;
    }

    /**
     *     * http://127.0.0.1:8788/g2-client/jira/info/query?key=NRTCG2
     * @param queryKey
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public ResultVO queryJiraVersion (@RequestParam(name="key") String queryKey) {
        ResultVO resultVO = null ;
        List<JiraVersionVO> jiraVersionVOList = jiraService.queryJiraVersion(queryKey) ;
        if (jiraVersionVOList == null){
            resultVO = ResultUtils.buildFail() ;
        }else {
            resultVO = ResultUtils.buildSuccess(jiraVersionVOList) ;
        }
        return resultVO ;
    }

}
