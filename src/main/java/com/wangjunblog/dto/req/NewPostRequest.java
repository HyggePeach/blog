package com.wangjunblog.dto.req;

import lombok.Data;

/**
 * @author wangjun
 * @date 2024/7/17 22:30
 *
 * 新建文章请求参数
 */
@Data
public class NewPostRequest {


    private String title;

    private String content;

    private String token;

}
