package com.hunantv.fw.utils;

public class SeqIdThreadLocal {
    private static final ThreadLocal<String> threadId = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return SeqID.rnd().toString();
        }
    };

    public static void set(String value) {
        threadId.set(value);
    }

    public static String get() {
        return threadId.get();
    }

    public static void clear() {
        threadId.remove();
    }
}
