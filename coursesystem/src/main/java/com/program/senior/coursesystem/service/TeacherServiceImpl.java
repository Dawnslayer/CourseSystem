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
public class TeacherServiceImpl implements TeacherService{
    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<CourseBean> getCourseHasOpened(int number, int year, int term) {
        return teacherMapper.courseOpened(number, year, term);
    }

    @Override
    public List<CourseBean> getCourseInExamine(int number, int year, int term) {
        return teacherMapper.courseInExamine(number, year, term);
    }

    @Override
    public int getStudentNumber(int id) {
        return teacherMapper.getStudentNumber(id);
    }

    @Override
    public int cancelCourse(int id) {
        return teacherMapper.deleteCourse(id);
    }

    @Override
    public int addNewCourse(String name, int number, String courseName, String property, int credit, String classroom, int year, int term, int week, String time, String content) {
        courseMapper.addNewCourse(courseName, property, credit, classroom, name, year, term, week, time, content);
        int id = courseMapper.getCourseId(courseName, property, credit, classroom, name, year, term, week, time, content);
        return teacherMapper.addCourse(name, number, id);
    }

    @Override
    public String getScoreState(int id) {
        return teacherMapper.getScoreStateById(id);
    }

    @Override
    public List<StudentBean> allStudentsOfCourse(int id) {
        return studentMapper.allStudentsOfCourse(id);
    }

    @Override
    public int giveScoreToStudent(int number, int id, int score, String grade, String gpa, String comment) {
        return teacherMapper.releaseScore(number, id, score, grade, gpa, comment);
    }

    @Override
    public int commitScoreToExamine(int id) {
        return teacherMapper.changeScoreState(id);
    }
}
