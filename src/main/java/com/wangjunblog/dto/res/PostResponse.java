package com.wangjunblog.dto.res;

import com.baomidou.mybatisplus.annotation.TableId;
import com.wangjunblog.dao.entity.Post;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wangjun
 * @date 2024/7/17 22:46
 */
@Data

public class PostResponse  {

    private Integer postId;

    /**
     *
     */
    private String title;

    /**
     *
     */
    private String content;

    /**
     *
     */
    private Integer userId;

    /**
     *
     */
    private LocalDateTime created;

    /**
     *
     */
    private LocalDateTime lastModified;
}
