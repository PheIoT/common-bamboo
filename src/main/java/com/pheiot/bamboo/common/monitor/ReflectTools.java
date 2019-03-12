package com.pheiot.bamboo.common.monitor;


import java.lang.reflect.Field;

/**
 * Created by garen on 2017/07/29.
 */
public class ReflectTools {

    /**
     * setProperty
     *
     * @param obj          obj
     * @param propertyName propertyName
     * @param value        value
     * @throws Exception Exception
     */
    public static void setProperty(Object obj, String propertyName, Object value) throws Exception {
        Class clazz = obj.getClass();
        Field field = clazz.getDeclaredField(propertyName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    /**
     * setProperty
     *
     * @param obj       obj
     * @param value     value
     * @param clazzName clazzName
     * @throws Exception Exception
     */
    public static void setProperty(Object obj, Object value, String clazzName) throws Exception {
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Field field = null;
        for (int i = 0; i < fields.length; i++) {
            String className = fields[i].getType().getSimpleName();
            if (className.equals(clazzName)) {
                field = fields[i];
                break;
            }
        }
        field.setAccessible(true);
        field.set(obj, value);
    }
}
