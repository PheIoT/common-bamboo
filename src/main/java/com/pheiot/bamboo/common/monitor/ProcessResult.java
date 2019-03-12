package com.pheiot.bamboo.common.monitor;

/**
 * Created by Peter on 2017/3/18.
 */
public class ProcessResult {
    private Integer point;
    private String msg;
    private String subTitle;
    private boolean exit = false;
    private boolean errorFlag = false;

    /**
     * constructor
     *
     * @param msg message
     */
    public ProcessResult(String msg) {
        this.msg = msg;
    }

    /**
     * constructor
     *
     * @param point point
     */
    public ProcessResult(Integer point) {
        this.point = point;
        this.msg = null;
    }

    /**
     * constructor
     *
     * @param point point
     * @param msg   message
     */
    public ProcessResult(Integer point, String msg) {
        this.point = point;
        this.msg = msg;
    }

    /**
     * constructor
     *
     * @param point    point
     * @param msg      message
     * @param subTitle sub title
     */
    public ProcessResult(Integer point, String msg, String subTitle) {
        this.point = point;
        this.msg = msg;
        this.subTitle = subTitle;
    }

    /**
     * constructor
     *
     * @param point    point
     * @param msg      message
     * @param subTitle sub title
     * @param exit     exit flag
     */
    public ProcessResult(Integer point, String msg, String subTitle, boolean exit) {
        this.point = point;
        this.msg = msg;
        this.subTitle = subTitle;
        this.exit = exit;
    }

    /**
     * constructor
     *
     * @param exit true to dispose dialog
     */
    public ProcessResult(boolean exit) {
        this.exit = exit;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public boolean isErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(boolean errorFlag) {
        this.errorFlag = errorFlag;
    }
}
