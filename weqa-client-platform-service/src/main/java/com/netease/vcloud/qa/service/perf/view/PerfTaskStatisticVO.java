package com.netease.vcloud.qa.service.perf.view;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/7 16:43
 */
public class PerfTaskStatisticVO {

    private Number max ;

    private Number min ;

    private Number avg ;

    private Number total ;

    private int count ;

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

    public Number getTotal() {
        return total;
    }

    public void setTotal(Number total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
