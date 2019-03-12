/*
 * Copyright (c) 2015. For Intelligent Group.
 */

package com.pheiot.bamboo.common.dto;

/**
 * @author Peter Li
 */
public class JsonResponseDto extends AbstractValueObject {
    private int code;
    private String message;
    private Object body;

    /**
     * Constructor.
     *
     * @param code    response code.
     * @param message response message
     */
    public JsonResponseDto(int code, String message) {
        super();
        this.code = code;
        this.message = message;
        this.body = null;
    }

    /**
     * Get the success response dto.
     *
     * @return JsonResponseDto
     */
    public static JsonResponseDto success() {
        return new JsonResponseDto(1, "success");
    }

    /**
     * Get the failure response dto.
     *
     * @return JsonResponseDto
     */
    public static JsonResponseDto failure() {
        return new JsonResponseDto(0, "failure");
    }


    public int getCode() {
        return code;
    }


    /**
     * Set the code and return self.
     *
     * @param code response code.
     * @return JsonResponseDto
     */
    public JsonResponseDto setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Set the message and return self.
     *
     * @param message response message.
     * @return JsonResponseDto
     */
    public JsonResponseDto setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getBody() {
        return body;
    }

    /**
     * Set the body and return self.
     *
     * @param body response body.
     * @return JsonResponseDto
     */
    public JsonResponseDto setBody(Object body) {
        this.body = body;
        return this;
    }

    /**
     * Set the code and message for current instance.
     *
     * @param code    response code.
     * @param message response message
     * @return JsonResponseDto
     */
    public JsonResponseDto with(int code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }
}
