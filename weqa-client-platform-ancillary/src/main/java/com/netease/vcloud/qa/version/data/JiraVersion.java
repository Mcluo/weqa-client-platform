package com.netease.vcloud.qa.version.data;

import org.joda.time.DateTime;

import java.net.URI;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/4 15:52
 */
public class JiraVersion {
    private URI self;

    private Long id;

    private String description;

    private String name;

    private String jiraKey;

    private boolean archived;

    private boolean released;

    private boolean overdue;

    private DateTime startDate;

    private DateTime releaseDate;

    private DateTime userStartDate;

    private DateTime userReleaseDate;

    private DateTime actualReleaseDate;

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJiraKey() {
        return jiraKey;
    }

    public void setJiraKey(String jiraKey) {
        this.jiraKey = jiraKey;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(DateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public DateTime getUserStartDate() {
        return userStartDate;
    }

    public void setUserStartDate(DateTime userStartDate) {
        this.userStartDate = userStartDate;
    }

    public DateTime getUserReleaseDate() {
        return userReleaseDate;
    }

    public void setUserReleaseDate(DateTime userReleaseDate) {
        this.userReleaseDate = userReleaseDate;
    }

    public DateTime getActualReleaseDate() {
        return actualReleaseDate;
    }

    public void setActualReleaseDate(DateTime actualReleaseDate) {
        this.actualReleaseDate = actualReleaseDate;
    }
}
