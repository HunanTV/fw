package com.hunantv.fw.log;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternParser;

public class FwPatternLayout extends PatternLayout {
    public FwPatternLayout() {
        super();
    }

    public FwPatternLayout(String pattern) {
        super(pattern);
    }

    @Override
    protected PatternParser createPatternParser(String pattern) {
        return new FwPatternParser(pattern);
    }

}
