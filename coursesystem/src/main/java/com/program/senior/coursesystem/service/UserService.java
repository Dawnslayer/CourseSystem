package com.program.senior.coursesystem.service;

import com.program.senior.coursesystem.entity.UserBean;

public interface UserService {
    UserBean loginIn(String name, int number, String password);
    int register(String name, int number, String password, String type);
}
