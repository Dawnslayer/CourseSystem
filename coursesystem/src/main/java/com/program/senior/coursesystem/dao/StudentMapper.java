package com.program.senior.coursesystem.dao;

import com.program.senior.coursesystem.entity.StudentBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StudentMapper {
    int chooseCourse(String name, int number, int id);
    int dropCourse(int id, int number);
    List<StudentBean> getCourseLearned(int number, int year, int term);
    List<StudentBean> allStudentsOfCourse(int id);
}
