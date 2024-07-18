package com.wangjunblog.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangjun
 * @date 2024/7/16 23:06
 *
 * 注册请求参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    public String userName;

    public String password;

    public String email;
}
