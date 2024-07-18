package com.wangjunblog.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangjun
 * @date 2024/7/17 23:11
 *
 * 更新文章请求参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostRequest {


    private Integer postId;


    private String title;

    private String content;

    private String token;

}
