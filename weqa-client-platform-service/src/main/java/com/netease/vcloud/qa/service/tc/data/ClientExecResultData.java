package com.netease.vcloud.qa.service.tc.data;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/14 10:09
 */
public class ClientExecResultData {

    private Long id ;

    private Long case_id ;

    private String name ;

    private Integer priority_id ;

    private Integer result_id ;

    private String last_executed_by_email ;

    private String last_executed_by_name ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCase_id() {
        return case_id;
    }

    public void setCase_id(Long case_id) {
        this.case_id = case_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority_id() {
        return priority_id;
    }

    public void setPriority_id(Integer priority_id) {
        this.priority_id = priority_id;
    }

    public Integer getResult_id() {
        return result_id;
    }

    public void setResult_id(Integer result_id) {
        this.result_id = result_id;
    }

    public String getLast_executed_by_email() {
        return last_executed_by_email;
    }

    public void setLast_executed_by_email(String last_executed_by_email) {
        this.last_executed_by_email = last_executed_by_email;
    }

    public String getLast_executed_by_name() {
        return last_executed_by_name;
    }

    public void setLast_executed_by_name(String last_executed_by_name) {
        this.last_executed_by_name = last_executed_by_name;
    }
}
