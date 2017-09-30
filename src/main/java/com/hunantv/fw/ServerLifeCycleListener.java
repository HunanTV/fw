package com.hunantv.fw;

public interface ServerLifeCycleListener {

    public void starting();

    public void started();

    public void failure(Throwable cause);

    public void stopping();

    public void stopped();

}
