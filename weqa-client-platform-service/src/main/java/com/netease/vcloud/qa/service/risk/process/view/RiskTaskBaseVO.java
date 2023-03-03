package com.netease.vcloud.qa.service.risk.process.view;

import com.netease.vcloud.qa.result.view.UserInfoVO;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/9 10:11
 */
public class RiskTaskBaseVO {

    private long id ;

    private String taskName ;

    private List<UserInfoVO> userList ;

    private String status ;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public List<UserInfoVO> getUserList() {
        return userList;
    }

    public void setUserList(List<UserInfoVO> userList) {
        this.userList = userList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
