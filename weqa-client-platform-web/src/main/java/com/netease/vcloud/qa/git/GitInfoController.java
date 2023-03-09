package com.netease.vcloud.qa.git;

import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.git.GitInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/13 15:30
 */
@RestController
@RequestMapping("/git/info")
public class GitInfoController {
    @Autowired
    private GitInfoService gitInfoService ;

    /**
     * http://127.0.0.1:8788/g2-client/git/info/branch/query?key=
     * @param projectId
     * @param queryKey
     * @return
     */
    @RequestMapping("/branch/query")
    @ResponseBody
    public ResultVO queryGitBranchList(@RequestParam(name = "id",required = false ,defaultValue = "49837") Long projectId,
                                       @RequestParam(name = "key",required = false,defaultValue = "") String queryKey){
        ResultVO resultVO = null ;
        List<String> gitBranchList = gitInfoService.queryGitBranchList(projectId, queryKey) ;
        if (gitBranchList != null) {
            resultVO = ResultUtils.buildSuccess(gitBranchList);
        }else {
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }

}
