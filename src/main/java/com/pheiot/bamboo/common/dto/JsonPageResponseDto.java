/*
 * Copyright (c) 2015. For Intelligent Group.
 */

package com.pheiot.bamboo.common.dto;

/**
 * @author Peter Li
 */
public class JsonPageResponseDto {
    private int code;
    private String message;
    private Object body;

    private long recordsTotal;
    private long recordsFiltered;
    private int draw;

    /**
     * Constructor.
     *
     * @param code    response code.
     * @param message response message
     */
    public JsonPageResponseDto(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    /**
     * Get the success page response dto.
     *
     * @return JsonPageResponseDto
     */
    public static JsonPageResponseDto success() {
        return new JsonPageResponseDto(1, "success");
    }

    /**
     * Get the failure page response dto.
     *
     * @return JsonPageResponseDto
     */
    public static JsonPageResponseDto failure() {
        return new JsonPageResponseDto(0, "failure");
    }


    public int getCode() {
        return code;
    }

    /**
     * Set the code and return self.
     *
     * @param code response code.
     * @return JsonPageResponseDto
     */
    public JsonPageResponseDto setCode(int code) {
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
     * @return JsonPageResponseDto
     */
    public JsonPageResponseDto setMessage(String message) {
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
     * @return JsonPageResponseDto
     */
    public JsonPageResponseDto setBody(Object body) {
        this.body = body;
        return this;
    }

    /**
     * Set the body and return self.
     *
     * @param code    response body.
     * @param message response message.
     * @return JsonPageResponseDto
     */
    public JsonPageResponseDto with(int code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

}
