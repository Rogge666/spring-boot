package com.rogge.service.impl;

import com.rogge.mapper.UserMapper;
import com.rogge.model.User;
import com.rogge.service.UserService;
import com.rogge.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
* [Description]
* <p>
* [How to use]
* <p>
* [Tips]
*
* @author Created by Rogge on 2017/10/06
* @since 1.0.0
*/
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Resource
    private UserMapper userMapper;

}
