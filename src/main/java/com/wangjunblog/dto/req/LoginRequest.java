package com.wangjunblog.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangjun
 * @date 2024/7/16 23:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    public String userName;

    public String password;

}
