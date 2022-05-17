package com.program.senior.coursesystem.dao;

import com.program.senior.coursesystem.entity.UserBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserBean getInfo(String name, int number, String password);
}
