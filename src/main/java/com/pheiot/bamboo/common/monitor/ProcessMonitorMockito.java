package com.pheiot.bamboo.common.monitor;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.Stubber;

/**
 * Created by garen on 2017/07/28.
 */
public class ProcessMonitorMockito extends Mockito {

    private ProcessMonitorListener processMonitorListener;

    private AbstractProcessMonitorMock abstractProcessMonitorMock = new AbstractProcessMonitorMock() {
    };


    /**
     * push
     *
     * @param processResult processResult
     * @return Mockito class Stubber
     */
    public Stubber push(ProcessResult processResult) {
        return doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                abstractProcessMonitorMock.push(processResult);
                return invocationOnMock.callRealMethod();
            }
        });
    }

    /**
     * push
     *
     * @param point point
     * @param msg   msg
     * @return Mockito class Stubber
     */
    public Stubber push(int point, String msg) {
        return doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                abstractProcessMonitorMock.push(point, msg);
                return invocationOnMock.callRealMethod();
            }
        });
    }

    /**
     * push
     *
     * @param point point
     * @return Mockito class Stubber
     */
    public Stubber push(int point) {
        return doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                abstractProcessMonitorMock.push(point);
                return invocationOnMock.callRealMethod();
            }
        });
    }

    /**
     * push
     *
     * @param msg msg
     * @return Mockito class Stubber
     */
    public Stubber push(String msg) {
        return doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                abstractProcessMonitorMock.push(msg);
                return invocationOnMock.callRealMethod();
            }
        });
    }

    /**
     * push
     *
     * @param point    point
     * @param msg      msg
     * @param subTitle subTitle
     * @return Mockito class Stubber
     */
    public Stubber push(int point, String msg, String subTitle) {
        return doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                abstractProcessMonitorMock.push(point, msg, subTitle);
                return invocationOnMock.callRealMethod();
            }
        });
    }

    /**
     * push
     *
     * @param exit exit
     * @return Mockito class Stubber
     */
    public Stubber push(boolean exit) {
        return doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                abstractProcessMonitorMock.push(exit);
                return invocationOnMock.callRealMethod();
            }
        });
    }

    /**
     * push
     *
     * @param point    point
     * @param msg      msg
     * @param subTitle subTitle
     * @param exit     exit
     * @return Mockito class Stubber
     */
    public Stubber push(int point, String msg, String subTitle, boolean exit) {
        return doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                abstractProcessMonitorMock.push(point, msg, subTitle, exit);
                return invocationOnMock.callRealMethod();
            }
        });
    }

    /**
     * pushSubTitle
     *
     * @param subTitle subTitle
     * @return Mockito class Stubber
     */
    public Stubber pushSubTitle(String subTitle) {
        return doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                abstractProcessMonitorMock.pushSubTitle(subTitle);
                return invocationOnMock.callRealMethod();
            }
        });
    }

    /**
     * pushErrorMsg
     *
     * @param msg msg
     * @return Mockito class Stubber
     */
    public Stubber pushErrorMsg(String msg) {
        return doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                abstractProcessMonitorMock.pushErrorMsg(msg);
                return invocationOnMock.callRealMethod();
            }
        });
    }

    /**
     * pushErrorMsg
     *
     * @param msg      msg
     * @param subTitle subTitle
     * @return Mockito class Stubber
     */
    public Stubber pushErrorMsg(String msg, String subTitle) {
        return doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                abstractProcessMonitorMock.pushErrorMsg(msg, subTitle);
                return invocationOnMock.callRealMethod();
            }
        });
    }

    /**
     * push
     *
     * @param process process
     * @param end     end
     * @param msg     msg
     * @return Mockito class Stubber
     */
    public Stubber push(int process, int end, String msg) {

        return doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {

                ThreadTask threadTask = new ThreadTask() {
                    @Override
                    public void run() {
                        for (int i = 0; i < end - process; i++) {
                            if (isStop()) {
                                break;
                            }
                            if (i == 0) {
                                abstractProcessMonitorMock.push(process, msg);
                            } else {
                                abstractProcessMonitorMock.push(process + i);
                            }
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        abstractProcessMonitorMock.push(end);
                    }
                };
                if (abstractProcessMonitorMock.getCurrentThread() != null && (abstractProcessMonitorMock.getCurrentThread().getState().equals(Thread.State.RUNNABLE) || abstractProcessMonitorMock.getCurrentThread().getState().equals(Thread.State.TIMED_WAITING))) {
                    abstractProcessMonitorMock.getCurrentThread().setStop();
                    abstractProcessMonitorMock.setCurrentThread(null);
                }
                abstractProcessMonitorMock.setCurrentThread(threadTask);
                threadTask.start();
                return invocationOnMock.callRealMethod();
            }
        });

    }

    /**
     * push
     *
     * @param process process
     * @param end     end
     * @param msg     msg
     * @param allTime allTime
     * @return Mockito class Stubber
     */
    public Stubber push(int process, int end, String msg, long allTime) {

        return doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                ThreadTask threadTask = new ThreadTask() {
                    @Override
                    public void run() {
                        double second = Math.ceil(allTime / 1000);
                        int average = (int) ((end - process) / second);
                        for (int i = 0; i < second; i++) {
                            if (isStop()) {
                                break;
                            }
                            if (i == 0) {
                                abstractProcessMonitorMock.push(process, msg);
                            } else {
                                abstractProcessMonitorMock.push(process + average * i);
                            }
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        abstractProcessMonitorMock.push(end);
                    }
                };
                if (abstractProcessMonitorMock.getCurrentThread() != null && (abstractProcessMonitorMock.getCurrentThread().getState().equals(Thread.State.RUNNABLE) || abstractProcessMonitorMock.getCurrentThread().getState().equals(Thread.State.TIMED_WAITING))) {
                    abstractProcessMonitorMock.getCurrentThread().setStop();
                    abstractProcessMonitorMock.setCurrentThread(null);
                }
                abstractProcessMonitorMock.setCurrentThread(threadTask);
                threadTask.start();
                return invocationOnMock.callRealMethod();
            }
        });

    }

    /**
     * addProcessMonitorListener
     *
     * @param l ProcessMonitorListener
     */
    public void addProcessMonitorListener(ProcessMonitorListener l) {
        processMonitorListener = l;
        abstractProcessMonitorMock.addProcessMonitorListener(processMonitorListener);
    }

}
