package com.netease.vcloud.qa.service.tag.data;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/8 16:02
 */
public class TagDTO {

    private Long id ;

    private String name ;

    private String type ;

    private String creator ;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
