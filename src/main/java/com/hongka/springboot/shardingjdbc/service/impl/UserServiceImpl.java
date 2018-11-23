package com.hongka.springboot.shardingjdbc.service.impl;

import com.hongka.springboot.shardingjdbc.entity.User;
import com.hongka.springboot.shardingjdbc.mapper.UserMapper;
import com.hongka.springboot.shardingjdbc.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author quxinyong
 * @since 2018-11-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
