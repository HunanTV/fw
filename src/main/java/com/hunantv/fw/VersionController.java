package com.hunantv.fw;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.hunantv.fw.result.RestfulResult;
import com.hunantv.fw.utils.StringUtil;
import com.hunantv.fw.view.View;

public class VersionController extends Controller {

    private static AppInfo appInfo = AppInfo.EMPTY;

    static {
        load();
    }

    private static void load() {
        try {
            Properties properties = new Properties();
            try(InputStream is = new FileInputStream(new File(Application.getInstance().getSysConf().getSysPath() + "confs/git.properties"))){
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
            appInfo.setName(appName);
            appInfo.setBranch(properties.getProperty("git.branch", "master"));
            appInfo.setBuildTime(properties.getProperty("git.build.time", ""));
            appInfo.setCommitId(properties.getProperty("git.commit.id", ""));
            appInfo.setCommitTime(properties.getProperty("git.commit.time", ""));
            appInfo.setVersion(properties.getProperty("git.build.version", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public View version(){
        return this.renderJson(new RestfulResult(appInfo));
    }
}
