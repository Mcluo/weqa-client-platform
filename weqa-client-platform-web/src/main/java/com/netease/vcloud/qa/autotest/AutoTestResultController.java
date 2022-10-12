package com.netease.vcloud.qa.autotest;


import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.dao.AutoTestResultDAO;
import com.netease.vcloud.qa.model.AutoTestResultDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auto")
public class AutoTestResultController {
    @Autowired
    private AutoTestResultDAO autoTestResultDAO ;

    /**
     *http://127.0.0.1:8080/auto/test/result/add?info=test_01_0zz
     * @param runInfo
     * @param caseName
     * @param caseDetail
     * @param success
     * @param fail
     * @param result
     * @return
     */
    @RequestMapping("/test/result/add")
    @ResponseBody
    public JSONObject addResultTest(@RequestParam(name = "info")  String runInfo ,
                                    @RequestParam(name = "case",required = false) String caseName ,
                                    @RequestParam(name = "detail",required = false)String caseDetail,
                                    @RequestParam(name = "success",required = false,defaultValue = "0") int success,
                                    @RequestParam(name = "fail",required = false,defaultValue = "0")int fail,
                                    @RequestParam(name = "result",required = false) String result) {
        JSONObject json = new JSONObject();
        AutoTestResultDO autoTestResultDO = new AutoTestResultDO() ;
        autoTestResultDO.setRunInfo(runInfo);
        autoTestResultDO.setCaseName(caseName);
        autoTestResultDO.setCaseDetail(caseDetail);
        autoTestResultDO.setSuccessNumber(success);
        autoTestResultDO.setFailNumber(fail);
        if (StringUtils.isNotBlank(result)){
            autoTestResultDO.setRunResult(result);
        }
        int count = autoTestResultDAO.insertIntoAutoTestResult(autoTestResultDO) ;
        if (count > 0){
            json.put("code",200) ;
        }else {
            json.put("code", 500) ;
        }
        return json ;
    }
}
