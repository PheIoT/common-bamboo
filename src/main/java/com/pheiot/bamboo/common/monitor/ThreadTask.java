package com.pheiot.bamboo.common.monitor;

/**
 * Created by garen on 2017/08/21.
 */
public abstract class ThreadTask extends Thread {

    private volatile boolean stop = false;

    /**
     * run
     */
    public abstract void run();

    public boolean isStop() {
        return stop;
    }

    /**
     * setStop
     */
    public void setStop() {
        this.stop = true;
    }
}
