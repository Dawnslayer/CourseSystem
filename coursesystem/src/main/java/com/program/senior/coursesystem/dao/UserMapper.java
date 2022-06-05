package com.program.senior.coursesystem.dao;

import com.program.senior.coursesystem.entity.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    UserBean getInfo(String name, int number, String password);
    int addUser(String name, int number, String password, String type);
    UserBean hasNumber(int number);
}
