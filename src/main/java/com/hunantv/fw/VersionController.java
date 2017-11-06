package com.hunantv.fw;

import com.hunantv.fw.result.RestfulResult;
import com.hunantv.fw.view.View;

public class VersionController extends Controller {

    public View version() {
        return this.renderJson(new RestfulResult(AppInfo.getInfo()));
    }
}
