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
    /**
     * 通过总数
     */
    private int passCount ;

    private List<TCTestSuitCoveredDetailVO> detailList ;

    public Long getTvID() {
        return tvID;
    }

    public void setTvID(Long tvID) {
        this.tvID = tvID;
    }

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

    public int getPassCount() {
        return passCount;
    }

    public void setPassCount(int passCount) {
        this.passCount = passCount;
    }
}
