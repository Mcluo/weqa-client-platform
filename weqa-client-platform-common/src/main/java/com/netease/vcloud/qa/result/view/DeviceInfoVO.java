package com.netease.vcloud.qa.result.view;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/22 15:34
 */
public class DeviceInfoVO {

    private Long id ;
    private String ip ;

    private Integer port ;

    private String platform ;

    private String userId ;

    private String cpu ;

    private String owner ;
    private UserInfoVO operator;

    private boolean run;

    private boolean alive;


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public UserInfoVO getOperator() {
        return operator;
    }

    public void setOperator(UserInfoVO operator) {
        this.operator = operator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }


    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
