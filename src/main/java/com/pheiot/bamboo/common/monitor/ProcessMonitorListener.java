package com.pheiot.bamboo.common.monitor;

/**
 * Created by Peter on 2017/3/18.
 */
public interface ProcessMonitorListener {

    /**
     * this method will be invoked when monitor push a result
     *
     * @param processResult process result
     */
    void onProcessChange(ProcessResult processResult);
}
