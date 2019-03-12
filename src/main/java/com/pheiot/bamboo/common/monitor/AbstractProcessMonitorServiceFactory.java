package com.pheiot.bamboo.common.monitor;

import com.pheiot.bamboo.common.utils.mapper.BeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by garen on 2017/08/01.
 * <p>
 * see demo at project ispc_desktop. module ispc.d.client. test package.
 * com.intelligent.ispc.client.ex.processmonitor
 * ProcessTest.java ProcessMonitorServiceFactoryTest.java
 */
public abstract class AbstractProcessMonitorServiceFactory {

    private final Logger logger = LoggerFactory.getLogger(AbstractProcessMonitorServiceFactory.class);

    private ProcessMonitorListener processMonitorListener;

    private Object[] filed;

    private Object service;

    /**
     * get Proxy Service
     *
     * @param service service
     * @param <T>     XXXServiceImpl
     * @return XXXServiceImpl
     */
    public <T> T getProxyService(T service) {

        try {
            this.service = service;
            T result = (T) service.getClass().newInstance();

            result = (T) BeanMapper.map(service, Object.class);

            setProxyFiled(service);
            Object[] args = new Object[filed.length];

            for (int i = 0; i < filed.length; i++) {
                Object object = ProcessMonitorMockito.spy(filed[i]);
                try {
                    ReflectTools.setProperty(result, object, object.getClass().getSimpleName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                args[i] = object;
            }

            getProcessMonitorMockito(args);

            return result;

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * set Proxy Filed
     *
     * @param c c
     * @return AbstractProcessMonitorServiceFactory
     */
    public AbstractProcessMonitorServiceFactory setProxyFiled(Object... c) {
        this.filed = c;
        return this;
    }

    private void getProcessMonitorMockito(Object... args) {
        ProcessMonitorMockito processMonitorMockito = new ProcessMonitorMockito();
        processMonitorMockito.addProcessMonitorListener(processMonitorListener);
        doProcess(processMonitorMockito, args);
    }

    /**
     * setProxyFiled
     *
     * @param service service
     */
    public abstract void setProxyFiled(Object service);

    /**
     * doProcess
     *
     * @param processMonitorMockito processMonitorMockito
     * @param args                  args
     */
    public abstract void doProcess(ProcessMonitorMockito processMonitorMockito, Object... args);

    /**
     * addProcessMonitorListener
     *
     * @param processMonitorListener processMonitorListener
     * @return AbstractProcessMonitorServiceFactory
     */
    public AbstractProcessMonitorServiceFactory addProcessMonitorListener(ProcessMonitorListener processMonitorListener) {
        this.processMonitorListener = processMonitorListener;
        return this;
    }

    public Object getService() {
        return service;
    }

}


