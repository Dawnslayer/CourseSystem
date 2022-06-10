package com.program.senior.coursesystem.service;

import com.program.senior.coursesystem.entity.CourseBean;
import com.program.senior.coursesystem.entity.StudentBean;

import java.util.List;

public interface TeacherService {
    List<CourseBean> getCourseHasOpened(int number, int year, int term);
    List<CourseBean> getCourseInExamine(int number, int year, int term);
    int getStudentNumber(int id);
    int cancelCourse(int id);
    int addNewCourse(String name, int number, String courseName, String property, int credit, String classroom,
                     int year, int term, int week, String time, String content);//插入2个表，id自动生成，course插入的返回值为id
    String getScoreState(int id);
    List<StudentBean> allStudentsOfCourse(int id);
    int giveScoreToStudent(int number, int id, int score, String grade, String gpa, String comment);
    int commitScoreToExamine(int id);
}
