package com.netease.vcloud.qa.service.auto.view;

import com.netease.vcloud.qa.model.VcloudClientQsRelationDO;
import com.netease.vcloud.qa.model.VcloudClientQsTaskDO;

import java.util.Date;
import java.util.List;

public class VcloudClientQsTaskVO {

    private VcloudClientQsTaskDO qsTaskDO;

    private List<VcloudClientQsRelationDO> qsRelationDOList;


    public VcloudClientQsTaskDO getQsTaskDO() {
        return qsTaskDO;
    }

    public void setQsTaskDO(VcloudClientQsTaskDO qsTaskDO) {
        this.qsTaskDO = qsTaskDO;
    }

    public List<VcloudClientQsRelationDO> getQsRelationDOList() {
        return qsRelationDOList;
    }

    public void setQsRelationDOList(List<VcloudClientQsRelationDO> qsRelationDOList) {
        this.qsRelationDOList = qsRelationDOList;
    }
}