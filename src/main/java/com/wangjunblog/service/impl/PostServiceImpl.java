package com.wangjunblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangjunblog.dao.entity.Post;
import com.wangjunblog.dao.mapper.PostMapper;
import com.wangjunblog.dto.req.NewPostRequest;
import com.wangjunblog.dto.req.UpdatePostRequest;
import com.wangjunblog.dto.res.PostResponse;
import com.wangjunblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.wangjunblog.common.constant.RedisCacheConstant.USER_ID_KEY;

/**
* @author HyggePeach
* @description 针对表【post】的数据库操作Service实现
* @createDate 2024-07-16 22:56:52
*/
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post>
    implements PostService {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void newPost(NewPostRequest newPostRequest) {
        Integer uid = Integer.parseInt(stringRedisTemplate.opsForValue().get(USER_ID_KEY + newPostRequest.getToken()));
        LocalDateTime now = LocalDateTime.now();
        Post post=Post.builder()
                .title(newPostRequest.getTitle())
                .content(newPostRequest.getContent())
                .created(now).lastModified(now).userId(uid).build();
        save(post);
    }

    @Override
    public Page<Post> getPosts(Long uid, int pageNum, int pageSize, boolean isAsc){
        Page<Post> pageInfo = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getUserId,uid);
        wrapper.orderBy(true, isAsc, Post::getCreated);
        Page<Post> page = page(pageInfo, wrapper);
        return page;
    }

    @Override
    public PostResponse getPost(Integer postId) {
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getPostId,postId);
        Post post = getOne(wrapper);
        PostResponse res = new PostResponse();
        BeanUtil.copyProperties(post,res);
        return res;
    }

    @Override
    public void deletePost(String token,Integer postId) {
        Integer uid = Integer.parseInt(stringRedisTemplate.opsForValue().get(USER_ID_KEY + token));
        LambdaQueryWrapper <Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getPostId,postId).eq(Post::getUserId,uid);
        remove(wrapper);
    }

    @Override
    public boolean updatePost(UpdatePostRequest updatePostRequest) {
        Integer uid = Integer.parseInt(stringRedisTemplate.opsForValue().get(USER_ID_KEY + updatePostRequest.getToken()));
        LambdaUpdateWrapper<Post> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Post::getPostId,updatePostRequest.getPostId()).eq(Post::getUserId,uid);

        wrapper.set(Post::getTitle,updatePostRequest.getTitle())
                .set(Post::getContent,updatePostRequest.getContent())
                .set(Post::getLastModified, LocalDateTime.now());
        return update(wrapper);

    }
}




