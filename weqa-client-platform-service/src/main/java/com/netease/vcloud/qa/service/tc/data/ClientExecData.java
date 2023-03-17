package com.netease.vcloud.qa.service.tc.data;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/13 20:02
 */
public class ClientExecData {

    private int code ;

    private List<ClientExecResultData> result ;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ClientExecResultData> getResult() {
        return result;
    }

    public void setResult(List<ClientExecResultData> result) {
        this.result = result;
    }
}
