package com.manage.service.service.impl;

import com.manage.service.entity.User;
import com.manage.service.mapper.UserMapper;
import com.manage.service.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xiu
 * @since 2021-12-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
