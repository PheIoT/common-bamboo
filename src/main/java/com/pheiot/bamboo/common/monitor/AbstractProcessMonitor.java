package com.pheiot.bamboo.common.monitor;

/**
 * @author Peter Li
 */
public abstract class AbstractProcessMonitor implements ProcessMonitor {
    private ThreadLocal<ProcessResult> threadLocal = new ThreadLocal<ProcessResult>();

    private ProcessMonitorListener processMonitorListener;

    @Override
    public void push(ProcessResult processResult) {
        threadLocal.set(processResult);
        if (processMonitorListener != null) {
            processMonitorListener.onProcessChange(threadLocal.get());
        }
    }

    @Override
    public void push(int point, String msg) {
        push(new ProcessResult(point, msg));
    }

    @Override
    public void push(int point) {
        push(new ProcessResult(point));
    }

    @Override
    public void push(String msg) {
        push(new ProcessResult(msg));
    }

    @Override
    public void push(int point, String msg, String subTitle) {
        push(new ProcessResult(point, msg, subTitle));
    }

    @Override
    public void push(boolean exit) {
        push(new ProcessResult(exit));
    }

    @Override
    public void push(int point, String msg, String subTitle, boolean exit) {
        push(new ProcessResult(point, msg, subTitle, exit));
    }

    @Override
    public void pushSubTitle(String subTitle) {
        push(new ProcessResult(null, null, subTitle));
    }

    @Override
    public void pushErrorMsg(String msg) {
        ProcessResult processResult = new ProcessResult(msg);
        processResult.setErrorFlag(true);
        push(processResult);
    }

    public void pushErrorMsg(String msg, String subTitle) {
        ProcessResult processResult = new ProcessResult(msg);
        processResult.setErrorFlag(true);
        processResult.setSubTitle(subTitle);
        push(processResult);
    }

    @Override
    public void addProcessMonitorListener(ProcessMonitorListener l) {
        this.processMonitorListener = l;
    }
}
