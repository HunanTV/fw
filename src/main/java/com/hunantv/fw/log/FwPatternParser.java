package com.hunantv.fw.log;

import org.apache.log4j.helpers.FormattingInfo;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LoggingEvent;

import com.hunantv.fw.utils.SeqIdThreadLocal;

public class FwPatternParser extends PatternParser {
    
    public FwPatternParser(String pattern) {
        super(pattern);
    }

    /**
     * 重写finalizeConverter，对特定的占位符进行处理，T表示线程ID占位符
     */
    @Override
    protected void finalizeConverter(char c) {
        if (c == 'T') {
            this.addConverter(new FwPatternConverter(this.formattingInfo));
        } else {
            super.finalizeConverter(c);
        }
    }

    private static class FwPatternConverter extends PatternConverter {
        
        public FwPatternConverter(FormattingInfo fi) {
            super(fi);
        }

        /**
         * 当需要显示线程ID的时候，返回当前调用线程的ID
         */
        @Override
        protected String convert(LoggingEvent event) {
            return String.valueOf(SeqIdThreadLocal.get());
        }
    }
    
}
