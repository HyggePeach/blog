package com.wangjunblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangjunblog.dao.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangjunblog.dto.req.NewPostRequest;
import com.wangjunblog.dto.req.UpdatePostRequest;
import com.wangjunblog.dto.res.PostResponse;

/**
* @author HyggePeach
* @description 针对表【post】的数据库操作Service
* @createDate 2024-07-16 22:56:52
*/
public interface PostService extends IService<Post> {


    public void newPost(NewPostRequest newPostRequest);
    public Page<Post> getPosts(Long uid, int page, int size, boolean isAsc);

    public PostResponse getPost(Integer postId);

    public void deletePost(String token,Integer postId);

    public boolean updatePost(UpdatePostRequest updatePostRequest);

}
