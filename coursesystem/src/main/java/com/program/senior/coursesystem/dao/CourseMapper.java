package com.program.senior.coursesystem.dao;

import com.program.senior.coursesystem.entity.CourseBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CourseMapper {
    List<CourseBean> courseCanChoose(int number, int year, int term);
    List<CourseBean> courseChosen(int number, int year, int term);
    CourseBean getCourseById(int id);
    int addNewCourse(String courseName, String property, int credit, String classroom, String name, int year, int term,
                     int week, String time, String content);
    int getCourseId(String courseName, String property, int credit, String classroom, String name, int year, int term,
                    int week, String time, String content);
    List<CourseBean> getCourseCanExamine();
    List<CourseBean> getScoreCanExamine();
}
