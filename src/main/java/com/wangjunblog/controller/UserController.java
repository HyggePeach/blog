package com.wangjunblog.controller;

import com.wangjunblog.common.web.Result;
import com.wangjunblog.dto.req.LoginRequest;

import com.wangjunblog.dto.req.RegisterRequest;

import com.wangjunblog.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

/**
 * @author wangjun
 * @date 2024/7/16 22:36
 */
@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserService usrService;

    @PostMapping("/api/auth/login")
    public Result login( @RequestBody LoginRequest loginRequest){
        return Result.success(usrService.login(loginRequest));
    }

    @PostMapping("/api/auth/register")
    public Result register(@RequestBody RegisterRequest registerRequest){
        usrService.register(registerRequest);
        return Result.success();
    }

    @GetMapping("/api/auth/me")
    public Result me(@RequestAttribute("token") String token){
        return Result.success(usrService.me(token));
    }

}
