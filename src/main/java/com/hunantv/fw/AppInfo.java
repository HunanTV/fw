package com.hunantv.fw;

public class AppInfo {

    private String name;
    private String buildTime;
    private String commitTime;
    private String commitId;
    private String commitMessage;
    private String closeTagName;
    private String version;
    private String branch;
    
    public static AppInfo EMPTY = new AppInfo();
    
    public AppInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getCloseTagName() {
        return closeTagName;
    }

    public void setCloseTagName(String closeTagName) {
        this.closeTagName = closeTagName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }
}
