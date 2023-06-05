package com.netease.vcloud.qa.service.risk.source.struct.view;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/6/5 11:18
 */
public class TCTestSuitCheckDataInfoVO implements CheckDataVOInterface{
    /**
     * 版定的tvID
     */
    private Long tvID ;

    /**
     * tc总数
     */
    private int tcTotal ;

    /**
     * 覆盖总数
     */
    private int autoCovered ;

    private List<TCTestSuitCoveredDetailVO> detailList ;

    public int getTcTotal() {
        return tcTotal;
    }

    public void setTcTotal(int tcTotal) {
        this.tcTotal = tcTotal;
    }

    public int getAutoCovered() {
        return autoCovered;
    }

    public void setAutoCovered(int autoCovered) {
        this.autoCovered = autoCovered;
    }

    public List<TCTestSuitCoveredDetailVO> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<TCTestSuitCoveredDetailVO> detailList) {
        this.detailList = detailList;
    }
}
