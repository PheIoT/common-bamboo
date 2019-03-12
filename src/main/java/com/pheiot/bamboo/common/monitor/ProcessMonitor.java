package com.pheiot.bamboo.common.monitor;

/**
 * Created by Peter on 2017/3/18.
 */
public interface ProcessMonitor {

    /**
     * method to push process result
     *
     * @param processResult process result
     */
    void push(ProcessResult processResult);

    /**
     * method to push percent and msg
     *
     * @param point 0 ~ 100
     * @param msg   log
     */
    void push(int point, String msg);

    /**
     * method to push percent
     *
     * @param point 0 ~ 100
     */
    void push(int point);

    /**
     * method to push log
     *
     * @param msg log
     */
    void push(String msg);

    /**
     * method to push percent, msg and subTitle
     *
     * @param point    0 ~ 100
     * @param msg      log
     * @param subTitle subTitle in process dialog
     */
    void push(int point, String msg, String subTitle);

    /**
     * method to manually close process dialog
     *
     * @param exit true to dispose process dialog
     */
    void push(boolean exit);

    /**
     * method to push percent, msg, subTitle, and exit flag
     *
     * @param point    0 ~ 100
     * @param msg      log
     * @param subTitle sub title
     * @param exit     true to dispose process dialog
     */
    void push(int point, String msg, String subTitle, boolean exit);

    /**
     * method to push subTitle
     *
     * @param subTitle subTitle
     */
    void pushSubTitle(String subTitle);

    /**
     * method ot push error message
     *
     * @param msg message
     */
    void pushErrorMsg(String msg);

    /**
     * method ot push error message
     *
     * @param msg      message
     * @param subTitle path
     */
    void pushErrorMsg(String msg, String subTitle);

    /**
     * method to add listener
     *
     * @param l listener
     */
    void addProcessMonitorListener(ProcessMonitorListener l);
}
