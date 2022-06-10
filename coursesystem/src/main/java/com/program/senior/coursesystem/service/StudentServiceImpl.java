package com.program.senior.coursesystem.service;

import com.program.senior.coursesystem.dao.CourseMapper;
import com.program.senior.coursesystem.dao.StudentMapper;
import com.program.senior.coursesystem.entity.CourseBean;
import com.program.senior.coursesystem.entity.StudentBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<CourseBean> courseCanChoose(int number, int year, int term) {
        return courseMapper.courseCanChoose(number, year, term);
    }

    @Override
    public List<CourseBean> courseChosen(int number, int year, int term) {
        return courseMapper.courseChosen(number, year, term);
    }

    @Override
    public int chooseCourse(String name, int number, int id) {
        return studentMapper.chooseCourse(name, number, id);
    }

    @Override
    public int dropCourse(int id, int number) {
        return studentMapper.dropCourse(id, number);
    }

    @Override
    public List<StudentBean> getCourseScore(int number, int year, int term) {
        return studentMapper.getCourseLearned(number, year, term);
    }
}
