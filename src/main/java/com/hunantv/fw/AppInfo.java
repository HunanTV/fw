package com.hunantv.fw;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.hunantv.fw.utils.FwLogger;
import com.hunantv.fw.utils.StringUtil;

class AppInfo {
    public final static FwLogger logger = new FwLogger(AppInfo.class);
    private String appName;
    private String buildTime;
    private String commitTime;
    private String commitId;
    private String commitMessage;
    private String closeTagName;
    private String version;
    private String branch;

    private static AppInfo appInfo = new AppInfo();

    public static AppInfo getInfo() {
        return appInfo;
    }

    static {
        try {
            Properties properties = new Properties();
            try (InputStream is = new FileInputStream(
                    new File(Application.getInstance().getSysConf().getSysPath() + "confs/git.properties"))) {
                properties.load(is);
            }

            String gitUrl = properties.getProperty("git.remote.origin.url", "");
            String appName = "Unknown";
            if (StringUtil.isNotBlank(gitUrl)) {
                try {
                    appName = gitUrl.split("/")[1].split("\\.")[0];
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            appInfo.setAppName(appName);
            appInfo.setBranch(properties.getProperty("git.branch", "master"));
            appInfo.setBuildTime(properties.getProperty("git.build.time", ""));
            appInfo.setCommitId(properties.getProperty("git.commit.id", ""));
            appInfo.setCommitTime(properties.getProperty("git.commit.time", ""));
            appInfo.setVersion(properties.getProperty("git.build.version", ""));
            appInfo.setCommitMessage(
                    new String(properties.getProperty("git.commit.message.full", "").getBytes("ISO-8859-1"), "utf-8"));
            appInfo.setCloseTagName(properties.getProperty("git.closest.tag.name", ""));
        } catch (Exception e) {
            logger.warn("File [git.properties] not found.");
        }
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
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
