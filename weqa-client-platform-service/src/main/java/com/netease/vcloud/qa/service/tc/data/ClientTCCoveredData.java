package com.netease.vcloud.qa.service.tc.data;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/6/5 11:26
 */
public class ClientTCCoveredData {

    private int total ;

    private int covered ;

    private int passed ;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCovered() {
        return covered;
    }

    public void setCovered(int covered) {
        this.covered = covered;
    }

    public int getPassed() {
        return passed;
    }

    public void setPassed(int passed) {
        this.passed = passed;
    }
}
