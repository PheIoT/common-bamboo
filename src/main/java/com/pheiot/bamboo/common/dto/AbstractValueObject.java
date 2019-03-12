package com.pheiot.bamboo.common.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * @author Peter Li
 */
@SuppressWarnings("serial")
public abstract class AbstractValueObject implements IValueObject {
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
