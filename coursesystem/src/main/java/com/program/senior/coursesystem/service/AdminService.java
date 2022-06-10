package com.program.senior.coursesystem.service;

import com.program.senior.coursesystem.entity.CourseBean;
import com.program.senior.coursesystem.entity.StudentBean;

import java.util.List;

public interface AdminService {
    List<CourseBean> getCourseCanExamine();
    CourseBean getCourseInfo(int id);
    int courseAccept(int id);
    int courseRefuse(int id);
    List<CourseBean> getScoreCanExamine();
    List<StudentBean> getStudentScore(int id);
    int scoreAccept(int id);
    int scoreRefuse(int id);
}
