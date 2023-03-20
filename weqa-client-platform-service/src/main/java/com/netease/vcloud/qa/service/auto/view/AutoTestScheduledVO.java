package com.netease.vcloud.qa.service.auto.view;

import com.netease.vcloud.qa.model.VcloudClientAutoTestScheduledRelationInfoDO;
import com.netease.vcloud.qa.model.VcloudClientScheduledTaskInfoDO;

import java.util.List;

public class AutoTestScheduledVO {
    private VcloudClientScheduledTaskInfoDO ScheduledTaskInfo;
    private List<VcloudClientAutoTestScheduledRelationInfoDO> scheduledRelationInfoDOList;

    public VcloudClientScheduledTaskInfoDO getScheduledTaskInfo() {
        return ScheduledTaskInfo;
    }

    public void setScheduledTaskInfo(VcloudClientScheduledTaskInfoDO scheduledTaskInfo) {
        ScheduledTaskInfo = scheduledTaskInfo;
    }

    public List<VcloudClientAutoTestScheduledRelationInfoDO> getScheduledRelationInfoDOList() {
        return scheduledRelationInfoDOList;
    }

    public void setScheduledRelationInfoDOList(List<VcloudClientAutoTestScheduledRelationInfoDO> scheduledRelationInfoDOList) {
        this.scheduledRelationInfoDOList = scheduledRelationInfoDOList;
    }
}
