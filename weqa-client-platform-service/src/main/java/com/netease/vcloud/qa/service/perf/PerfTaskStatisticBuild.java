package com.netease.vcloud.qa.service.perf;

import com.netease.vcloud.qa.service.perf.view.PerfTaskStatisticVO;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/7 20:03
 */
public class PerfTaskStatisticBuild {

    private Number max = Double.MIN_VALUE;

    private Number min = Double.MAX_VALUE;

    private Number total = 0;

    private int count = 0;

    public void add(Number one){
        if (one == null){
            return;
        }
        count++ ;
        total = total.doubleValue() + one.doubleValue() ;
        if (one.doubleValue() > max.doubleValue()){
            max = one ;
        }
        if (one.doubleValue() < min.doubleValue()){
            min = one ;
        }
    }

    public Number getMax() {
        return max;
    }

    public Number getMin() {
        return min;
    }

    public Number getTotal() {
        return total;
    }

    public int getCount() {
        return count;
    }

    public Number getAvg(){
        if (count <= 0){
            return 0 ;
        }else {
            return total.doubleValue() / (double)count ;
        }
    }

    public PerfTaskStatisticVO build(){
        PerfTaskStatisticVO perfTaskStatisticVO = new PerfTaskStatisticVO() ;
        perfTaskStatisticVO.setMax(this.getMax());
        perfTaskStatisticVO.setMin(this.getMin());
        perfTaskStatisticVO.setAvg(this.getAvg());
        perfTaskStatisticVO.setCount(this.getCount());
        perfTaskStatisticVO.setTotal(this.getTotal());
        return perfTaskStatisticVO ;
    }
}
