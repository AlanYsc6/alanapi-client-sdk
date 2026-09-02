package com.alan.alanapiclientsdk.exception;

import lombok.Getter;

/**
 * 接口调用异常：服务端返回非成功 code 时抛出，携带服务端的错误码与错误信息
 *
 * @author alan
 */
@Getter
public class ApiException extends RuntimeException {

    private final int code;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

}
