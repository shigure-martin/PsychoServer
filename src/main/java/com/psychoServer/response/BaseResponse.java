package com.psychoServer.response;

import java.util.ArrayList;
import java.util.Collection;

public class BaseResponse<E> {
    protected Collection<E> datas;
    protected String msg;
    protected String code;

    public BaseResponse() {

    }

    public BaseResponse(Collection<E> datas) {
        this.datas = datas;
    }

    public BaseResponse(String msg) {
        this.msg = msg;
    }

    public BaseResponse(Collection<E> datas, String msg) {
        this.datas = datas;
        this.msg = msg;
    }

    public BaseResponse(E data) {
        ArrayList<E> list = new ArrayList<E>(1);
        list.add(data);
        this.datas = list;
    }

    public Iterable<E> getDatas() {
        return datas;
    }

    public void setDatas(Collection<E> data) {
        this.datas = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
