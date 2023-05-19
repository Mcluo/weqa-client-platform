package com.netease.vcloud.qa.service.perf.view;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/19 16:09
 */
public class PerfBaseLineDetailVO {
    private Long id ;

    private String name ;

    //具体信息(或者子类)：

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
