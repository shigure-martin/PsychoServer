package com.psychoServer.response;

public class ErrorResponse<E> extends BaseResponse {
    private static final String ERROR_CODE = "500";

    public ErrorResponse() {
        this.code = ERROR_CODE;
    }

    public ErrorResponse(String msg) {
        super(msg);
        this.code = ERROR_CODE;
    }

    public ErrorResponse(Iterable<E> datas) {
        super(datas);
        this.code = ERROR_CODE;
    }

    public ErrorResponse(E data) {
        super(data);
        this.code = ERROR_CODE;
    }

    public ErrorResponse(String msg, String code) {
        super(msg);
        this.code = code;
    }
}

