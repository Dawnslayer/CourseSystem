package com.program.senior.coursesystem.service;

import com.program.senior.coursesystem.entity.CourseBean;
import com.program.senior.coursesystem.entity.StudentBean;

import java.util.List;

public interface StudentService {
    List<CourseBean> courseCanChoose(int number, int year, int term);
    List<CourseBean> courseChosen(int number, int year, int term);
    int chooseCourse(String name, int number, int id);
    int dropCourse(int id, int number);
    List<StudentBean> getCourseScore(int number, int year, int term);
}
