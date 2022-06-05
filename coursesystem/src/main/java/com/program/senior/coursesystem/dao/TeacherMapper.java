package com.program.senior.coursesystem.dao;

import com.program.senior.coursesystem.entity.CourseBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TeacherMapper {
    List<CourseBean> courseOpened(int number, int year, int term);
    List<CourseBean> courseInExamine(int number, int year, int term);
    int getStudentNumber(int id);
    int deleteCourse(int id);
    int addCourse(String name, int number, int id);
    String getScoreStateById(int id);
    int changeScoreState(int id);
    int releaseScore(int number, int id, int score, String grade, String gpa, String comment);
    int courseAccept(int id);
    int scoreAccept(int id);
    int scoreRefuse(int id);
}
