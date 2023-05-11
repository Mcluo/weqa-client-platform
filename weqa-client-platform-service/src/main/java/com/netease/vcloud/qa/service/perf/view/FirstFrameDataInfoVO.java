package com.netease.vcloud.qa.service.perf.view;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/11 16:58
 */
public class FirstFrameDataInfoVO {

    private String type ;

    private List<Long> detail ;

    private int count ;

    private Long avg ;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Long> getDetail() {
        return detail;
    }

    public void setDetail(List<Long> detail) {
        this.detail = detail;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getAvg() {
        return avg;
    }

    public void setAvg(Long avg) {
        this.avg = avg;
    }
}
