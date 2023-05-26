package com.netease.vcloud.qa.service.perf.data;

import java.text.DecimalFormat;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/23 11:09
 */
public class PerfTestReportData {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00") ;

    private Number max ;

    private Number min ;

    private Number avg ;

    private Integer count ;

    private Number baseMax ;

    private Number baseMin ;

    private Number baseAvg ;

    public Number getMax() {
        if (max == null){
            return null ;
        }
        return Double.parseDouble(DECIMAL_FORMAT.format(max.doubleValue()));
    }

    public void setMax(Number max) {
        this.max = max;
    }

    public Number getMin() {
//        return min;
        if (min == null){
            return null ;
        }
        return Double.parseDouble(DECIMAL_FORMAT.format(min.doubleValue()));

    }

    public void setMin(Number min) {
        this.min = min;
    }

    public Number getAvg() {
        if (avg == null) {
            return null;
        }
        return Double.parseDouble(DECIMAL_FORMAT.format(avg.doubleValue()));

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
        if (baseMax == null) {
            return null;
        }
        return Double.parseDouble(DECIMAL_FORMAT.format(baseMax.doubleValue()));
    }

    public void setBaseMax(Number baseMax) {
        this.baseMax = baseMax;
    }

    public Number getBaseMin() {
        if (baseMin==null) {
            return null;
        }
        return Double.parseDouble(DECIMAL_FORMAT.format(baseMin.doubleValue()));
    }

    public void setBaseMin(Number baseMin) {
        this.baseMin = baseMin;
    }

    public Number getBaseAvg() {
        if (baseAvg==null) {
            return null;
        }
        return Double.parseDouble(DECIMAL_FORMAT.format(baseAvg.doubleValue()));
    }

    public void setBaseAvg(Number baseAvg) {
        this.baseAvg = baseAvg;
    }

}
