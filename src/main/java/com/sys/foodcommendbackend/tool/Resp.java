package com.sys.foodcommendbackend.tool;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 通用响应实体
 *
 * @param <T> 泛型参数
 * @author LuoRuiJie
 * @since 2019-06-14 10:24:12
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Resp<T> {
    /**
     * 成功响应值
     */
    public static final Integer SUCCESS = 0;

    /**
     * 失败响应值
     */
    public static final Integer ERROR = 1;


    /**
     * 状态码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String errMsg;

    /**
     * 通用泛型数据
     */
    private T data;

    public Integer getCode() {
        return code;
    }

    public Resp<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public Resp<T> setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }

    public T getData() {
        return data;
    }

    public Resp<T> setData(T data) {
        this.data = data;
        return this;
    }

    /**
     * 成功响应，并携带数据
     *
     * @param data 数据
     * @param <T>  泛型参数
     * @return 响应实体
     * @author 辉耀
     * @since 2019-06-14 10:20:42
     */
    public static <T> Resp<T> ok(T data) {
        return new Resp<T>()
                .setCode(SUCCESS)
                .setData(data);
    }

    /**
     * 成功响应
     *
     * @return 响应实体
     * @author 辉耀
     * @since 2019-06-14 10:21:48
     */
    public static <T> Resp<T> ok() {
        return new Resp<T>()
                .setCode(SUCCESS);
    }

    /**
     * 失败响应
     *
     * @param msg 错误提示消息
     * @return 响应实体
     * @author 辉耀
     * @since 2019-06-14 10:23:16
     */
    public static <T> Resp<T> err(String msg) {
        return new Resp<T>()
                .setCode(ERROR)
                .setErrMsg(msg);
    }

    /**
     * 失败响应
     *
     * @param msg  错误提示消息
     * @param data 错误参数数据
     * @return 响应实体
     * @author 辉耀
     * @since 2019-6-14 22:24:03
     */
    public static <T> Resp<T> err(String msg, T data) {
        return new Resp<T>()
                .setCode(ERROR)
                .setData(data)
                .setErrMsg(msg);
    }

    @Override
    public String toString() {
        return "Resp{" +
                "code=" + code +
                ", errMsg='" + errMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
