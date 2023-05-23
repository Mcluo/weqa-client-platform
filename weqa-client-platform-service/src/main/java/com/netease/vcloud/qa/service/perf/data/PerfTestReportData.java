package com.netease.vcloud.qa.service.perf.data;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/23 11:09
 */
public class PerfTestReportData {

    private Number max ;

    private Number min ;

    private Number avg ;

    private Integer count ;

    private Number baseMax ;

    private Number baseMin ;

    private Number baseAvg ;

    public Number getMax() {
        return max;
    }

    public void setMax(Number max) {
        this.max = max;
    }

    public Number getMin() {
        return min;
    }

    public void setMin(Number min) {
        this.min = min;
    }

    public Number getAvg() {
        return avg;
    }

    public void setAvg(Number avg) {
        this.avg = avg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Number getBaseMax() {
        return baseMax;
    }

    public void setBaseMax(Number baseMax) {
        this.baseMax = baseMax;
    }

    public Number getBaseMin() {
        return baseMin;
    }

    public void setBaseMin(Number baseMin) {
        this.baseMin = baseMin;
    }

    public Number getBaseAvg() {
        return baseAvg;
    }

    public void setBaseAvg(Number baseAvg) {
        this.baseAvg = baseAvg;
    }
}
