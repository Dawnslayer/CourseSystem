package com.program.senior.coursesystem.service;

import com.program.senior.coursesystem.dao.CourseMapper;
import com.program.senior.coursesystem.dao.StudentMapper;
import com.program.senior.coursesystem.dao.TeacherMapper;
import com.program.senior.coursesystem.entity.CourseBean;
import com.program.senior.coursesystem.entity.StudentBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<CourseBean> getCourseCanExamine() {
        return courseMapper.getCourseCanExamine();
    }

    @Override
    public CourseBean getCourseInfo(int id) {
        return courseMapper.getCourseById(id);
    }

    @Override
    public int courseAccept(int id) {
        return teacherMapper.courseAccept(id);
    }

    @Override
    public int courseRefuse(int id) {
        return teacherMapper.deleteCourse(id);
    }

    @Override
    public List<CourseBean> getScoreCanExamine() {
        return courseMapper.getScoreCanExamine();
    }

    @Override
    public List<StudentBean> getStudentScore(int id) {
        return studentMapper.allStudentsOfCourse(id);
    }

    @Override
    public int scoreAccept(int id) {
        return teacherMapper.scoreAccept(id);
    }

    @Override
    public int scoreRefuse(int id) {
        return teacherMapper.scoreRefuse(id);
    }
}
