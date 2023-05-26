package com.netease.vcloud.qa.service.perf.view;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/19 16:29
 */
public class PerfBasePerfTaskInfoVO {

    private String Type ;

    private Long id ;

    private String name ;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

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
