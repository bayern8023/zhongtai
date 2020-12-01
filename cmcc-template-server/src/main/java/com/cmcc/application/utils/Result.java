package com.cmcc.application.utils;

import com.cmcc.application.enums.StatusCode;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author baiyanmin
 * @description: 返回集
 * @date 2020-09-12
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer code = 200;

    private String msg = "操作成功";

    private String description;

    private T data;

    public Result() {
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public Result setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Result setDescription(String description) {
        this.description = description;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }

    public static Result ok() {
        return new Builder()
                .code(StatusCode.SUCCESS.getCode())
                .msg(StatusCode.SUCCESS.getMsg())
                .build();
    }

    public static Result failure() {
        return new Builder()
                .code(StatusCode.FAILURE.getCode())
                .msg(StatusCode.FAILURE.getMsg())
                .build();
    }


    public static Result error() {
        return new Builder()
                .code(StatusCode.ERROR.getCode())
                .msg(StatusCode.ERROR.getMsg())
                .build();
    }

    private Result(Builder builder) {
        this.code = builder.code;
        this.msg = builder.msg;
        this.description = builder.description;
        this.data = (T) builder.data;
    }

    public static class Builder<T> {
        private Integer code;
        private String msg;
        private String description;
        private T data;

        public Builder code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder data(T data) {
            this.data = data;
            return this;
        }

        public Result build() {
            return new Result(this);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", description='" + description + '\'' +
                ", data=" + data +
                '}';
    }
}
