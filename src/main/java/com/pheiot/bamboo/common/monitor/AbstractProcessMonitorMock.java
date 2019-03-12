package com.pheiot.bamboo.common.monitor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Peter Li
 */
public abstract class AbstractProcessMonitorMock implements ProcessMonitor {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private volatile ThreadTask currentThread;

    private ProcessMonitorListener processMonitorListener;

    private AtomicInteger maxPoint = new AtomicInteger(0);

    private boolean errorFlag = false;

    private AtomicInteger errorAmount = new AtomicInteger(0);

    @Override
    public void push(ProcessResult processResult) {
        if (processResult.isErrorFlag() || maxPoint.get() <= processResult.getPoint()) {
            if (!errorFlag && !processResult.isErrorFlag()) {
                maxPoint.getAndSet(processResult.getPoint());
            } else {
                errorFlag = true;
            }
            if (errorAmount.get() < 1 && processMonitorListener != null) {
                if (errorFlag) {
                    errorAmount.incrementAndGet();
                }
                processMonitorListener.onProcessChange(processResult);
            }
        } else if (processResult.getPoint() != null && maxPoint.get() > processResult.getPoint()) {
            if (currentThread != null && (currentThread.getState().equals(Thread.State.RUNNABLE) || currentThread.getState().equals(Thread.State.TIMED_WAITING))) {
                currentThread.setStop();
                currentThread = null;
            }
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

    /**
     * pushErrorMsg
     *
     * @param msg      message
     * @param subTitle path
     */
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

    public void setCurrentThread(ThreadTask currentThread) {
        this.currentThread = currentThread;
    }

    public ThreadTask getCurrentThread() {
        return currentThread;
    }
}
