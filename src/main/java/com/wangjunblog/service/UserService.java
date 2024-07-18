package com.wangjunblog.service;

import com.wangjunblog.dao.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangjunblog.dto.req.LoginRequest;
import com.wangjunblog.dto.req.RegisterRequest;
import com.wangjunblog.dto.res.LoginResponse;
import com.wangjunblog.dto.res.UserInfoResponse;

//
//*
// * @author HyggePeach
// * @description 针对表【user】的数据库操作Service
// * @createDate 2024-07-16 22:56:32
//


public interface UserService extends IService<User> {

    public void register(RegisterRequest registerRequest);

    public LoginResponse login(LoginRequest loginRequest);

    public UserInfoResponse me(String token);
}
