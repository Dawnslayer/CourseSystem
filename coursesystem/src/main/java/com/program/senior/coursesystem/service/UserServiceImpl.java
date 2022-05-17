package com.program.senior.coursesystem.service;

import com.program.senior.coursesystem.dao.UserMapper;
import com.program.senior.coursesystem.entity.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserBean loginIn(String name, int number, String password) {
        return userMapper.getInfo(name, number, password);
    }
}
