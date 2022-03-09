package com.psychoServer.response;

import java.util.Collection;

public class SuccessResponse<E> extends BaseResponse {
    private static final String SUCCESS_CODE = "000";

    public SuccessResponse() {
        this.code = SUCCESS_CODE;
    }

    protected Integer totalRecords;

    public SuccessResponse(String msg) {
        super(msg);
        this.code = SUCCESS_CODE;
    }


    public SuccessResponse(Collection<E> datas) {
        this(datas, datas.size());
    }

    public SuccessResponse(Collection<E> datas, Integer totalRecords) {
        super(datas);
        this.code = SUCCESS_CODE;
        this.totalRecords = totalRecords;
    }


    public SuccessResponse(E data) {
        super(data);
        this.code = SUCCESS_CODE;
    }

    public SuccessResponse(E data, String msg) {
        super(data);
        this.code = SUCCESS_CODE;
        this.msg = msg;
    }

    public SuccessResponse(String msg, String code) {
        super(msg);
        this.code = code;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }
}
