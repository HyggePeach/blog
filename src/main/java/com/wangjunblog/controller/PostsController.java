package com.wangjunblog.controller;

import com.wangjunblog.common.web.Result;
import com.wangjunblog.dto.req.NewPostRequest;
import com.wangjunblog.dto.req.UpdatePostRequest;
import com.wangjunblog.service.PostService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangjun
 * @date 2024/7/16 22:37
 */
@RestController
public class PostsController {

    @Resource
    private PostService postService;

    @RequestMapping(value = "/api/posts",method = RequestMethod.POST)
    public Result newPost(@RequestAttribute("token") String token,@RequestBody NewPostRequest newPostRequest){
        newPostRequest.setToken(token);
        postService.newPost(newPostRequest);
        return Result.success();
    }


    @RequestMapping(value = "/api/posts",method = RequestMethod.GET)
    public Result getPosts(@RequestParam Long uid,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size,
                           @RequestParam(defaultValue = "true") boolean isAsc){

        return Result.success(postService.getPosts(uid,page,size,isAsc));
    }

    @RequestMapping(value = "/api/posts/{postId}",method = RequestMethod.GET)
    public Result getPost(@PathVariable Integer postId){
        return Result.success(postService.getPost(postId));
    }

    @RequestMapping(value = "/api/posts",method = RequestMethod.PUT)
    public Result updatePost(@RequestAttribute("token") String token,@RequestBody UpdatePostRequest updatePostRequest){
        updatePostRequest.setToken(token);
        postService.updatePost(updatePostRequest);
        return Result.success();
    }

    @RequestMapping(value = "/api/posts",method = RequestMethod.DELETE)
    public Result deletePost(@RequestAttribute("token") String token,@RequestParam Integer postId){
        postService.deletePost(token,postId);
        return Result.success();
    }
}
