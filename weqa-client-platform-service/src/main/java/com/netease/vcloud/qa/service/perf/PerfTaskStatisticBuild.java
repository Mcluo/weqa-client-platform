package com.netease.vcloud.qa.service.perf;

import com.netease.vcloud.qa.CommonData;
import com.netease.vcloud.qa.service.perf.view.PerfTaskDetailInfoVO;
import com.netease.vcloud.qa.service.perf.view.PerfTaskStatisticVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/7 20:03
 */
public class PerfTaskStatisticBuild {

    List<Number> dataList = new ArrayList<Number>() ;

    List<String> xAxisList = new ArrayList<String>() ;

    private Number max = Integer.MIN_VALUE;

    private Number min = Integer.MAX_VALUE;

    private Number total = 0;

    private int count = 0;


    public void add(String key,Number one){
        xAxisList.add(key) ;
        dataList.add(one) ;
        this.add(one);
    }

    private void add(Number one){
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
        if (count == 0){
            return 0 ;
        }
        return max;
    }

    public Number getMin() {
        if (count == 0){
            return 0 ;
        }
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
            int avg = (int)(total.doubleValue() * 100 / count) ;
//            return Double.parseDouble(CommonData.NUMBER_FORMAT.format(avg)) ;
            double avgDouble = (double) avg / (double)100 ;
            return avgDouble ;
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


    public PerfTaskDetailInfoVO buildDetailList(){
        PerfTaskDetailInfoVO perfTaskDetailInfoVO = new PerfTaskDetailInfoVO() ;
        perfTaskDetailInfoVO.setxAxis(xAxisList);
        perfTaskDetailInfoVO.setData(dataList);
        return perfTaskDetailInfoVO ;
    }
}
