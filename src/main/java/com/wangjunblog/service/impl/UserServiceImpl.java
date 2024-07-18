package com.wangjunblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangjunblog.dao.entity.User;
import com.wangjunblog.dto.req.LoginRequest;
import com.wangjunblog.dto.req.RegisterRequest;
import com.wangjunblog.dao.mapper.UserMapper;
import com.wangjunblog.dto.res.LoginResponse;
import com.wangjunblog.dto.res.UserInfoResponse;

import com.wangjunblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static com.wangjunblog.common.constant.RedisCacheConstant.USER_ID_KEY;
import static com.wangjunblog.common.constant.RedisCacheConstant.USER_LOGIN_KEY;

/**
 * @author HyggePeach
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-07-16 22:56:52
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(RegisterRequest registerRequest) {
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .username(registerRequest.userName)
                .password(SecureUtil.md5(registerRequest.password))
                .email(registerRequest.email)
                .created(now)
                .lastModified(now).build();
        userMapper.insert(user);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery(User.class)
                .eq(User::getUsername,loginRequest.getUserName())
                .eq(User::getPassword,SecureUtil.md5(loginRequest.getPassword()));
        User user = userMapper.selectOne(queryWrapper);
        if(user==null){
            throw new RuntimeException();
        }

        /**
         * 自定义token
         *
         * Hash
         * Key：login_用户名
         * Value：
         *  Key：token标识
         *  Val：JSON 字符串（用户信息）
         */

        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(USER_LOGIN_KEY + user.getUsername(), uuid);
        //token与用户id映射
        stringRedisTemplate.opsForValue().set(USER_ID_KEY+uuid, user.getUserId().toString());
        stringRedisTemplate.expire(USER_LOGIN_KEY + loginRequest.getUserName(), 30L, TimeUnit.DAYS);
        stringRedisTemplate.expire(USER_ID_KEY+uuid, 30L, TimeUnit.DAYS);

        return new LoginResponse(uuid,user.getUsername());
    }

    @Override
    public UserInfoResponse me(String token) {

        String uid = stringRedisTemplate.opsForValue().get(USER_ID_KEY + token);

        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery(User.class)
                .eq(User::getUserId,uid);

        User user = userMapper.selectOne(queryWrapper);
        UserInfoResponse res=new UserInfoResponse();
        BeanUtil.copyProperties(user,res);
        return res;
    }
}
