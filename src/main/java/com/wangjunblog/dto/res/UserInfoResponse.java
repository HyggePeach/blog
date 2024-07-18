package com.wangjunblog.dto.res;

import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

/**
 * @author wangjun
 * @date 2024/7/17 22:14
 */
public class UserInfoResponse {

    private Integer userId;

    private String username;

    private String email;

    private Date created;

    private Date lastModified;

}
