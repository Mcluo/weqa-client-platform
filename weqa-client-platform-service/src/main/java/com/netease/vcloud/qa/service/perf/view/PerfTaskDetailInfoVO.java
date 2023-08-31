package com.netease.vcloud.qa.service.perf.view;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/31 11:35
 */
public class PerfTaskDetailInfoVO {

    private List<Number> data ;

    private List<String> xAxis ; ;

    public List<Number> getData() {
        return data;
    }

    public void setData(List<Number> data) {
        this.data = data;
    }

    public List<String> getxAxis() {
        return xAxis;
    }

    public void setxAxis(List<String> xAxis) {
        this.xAxis = xAxis;
    }
}
