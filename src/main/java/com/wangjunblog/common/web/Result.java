package com.wangjunblog.common.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangjun
 * @date 2024/7/16 23:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private boolean status;  // 响应状态

    private String message;  // 返回信息，例如"操作成功"

    private T data;  // 返回的数据


    public static <T> Result<T> success() {
        return new Result<>(true,"",null);
    }
    public static <T> Result<T> success(T data) {
        return new Result<>(true, "操作成功", data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(true, message, data);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(false, message, null);
    }

    public static <T> Result<T> error(String message, T data) {
        return new Result<>(false, message, data);
    }

    // Getters and setters
    public boolean isSuccess() {
        return status;
    }

}
