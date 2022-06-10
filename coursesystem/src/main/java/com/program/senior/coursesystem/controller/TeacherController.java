package com.program.senior.coursesystem.controller;

import com.program.senior.coursesystem.entity.CourseBean;
import com.program.senior.coursesystem.entity.StudentBean;
import com.program.senior.coursesystem.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@Api(value = "老师控制器", tags = {"老师"})
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    private String weekToString(int week){
        switch (week){
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 3:
                return "周三";
            case 4:
                return "周四";
            case 5:
                return "周五";
            case 6:
                return "周六";
            case 7:
                return "周日";
            default:
                return "";
        }
    }

    @RequestMapping(value = "/courseTeaching", method = RequestMethod.POST)
    @ApiOperation(value = "管理课程界面")
    public String myCoursesPage(@ApiParam(value = "工号") int number, int year, int term, Model model){
        List<CourseBean> openResult = teacherService.getCourseHasOpened(number, year, term);
        List<CourseBean> examineResult = teacherService.getCourseInExamine(number, year, term);
        List<List<String>> courseOpen = new ArrayList<>();
        List<List<String>> courseExamine = new ArrayList<>();
        for(CourseBean cb : openResult){
            List<String> list = new ArrayList<>();
            list.add(String.valueOf(cb.getId()));
            list.add(cb.getCourseName());
            list.add(cb.getProperty());
            list.add(String.valueOf(cb.getCredit()));
            list.add(weekToString(cb.getWeek())+cb.getTime());
            list.add(cb.getClassroom());
            int num = teacherService.getStudentNumber(cb.getId());
            list.add(String.valueOf(num));
            courseOpen.add(list);
        }
        for(CourseBean cb : examineResult){
            List<String> list = new ArrayList<>();
            list.add(String.valueOf(cb.getId()));
            list.add(cb.getCourseName());
            list.add(cb.getProperty());
            list.add(String.valueOf(cb.getCredit()));
            list.add(weekToString(cb.getWeek())+cb.getTime());
            list.add(cb.getClassroom());
            courseExamine.add(list);
        }
        model.addAttribute("courseHasOpened", courseOpen);
        model.addAttribute("courseInExamine", courseExamine);
        return "manageCourse";
    }

    @RequestMapping(value = "/deleteCourse", method = RequestMethod.POST)
    @ApiOperation(value = "取消开课")
    public String cancel(int id, int number, @ApiParam(value = "学年") int year, @ApiParam(value = "学期") int term, Model model){
        teacherService.cancelCourse(id);
        return myCoursesPage(number, year, term, model);
    }

    @RequestMapping("/addCourse")
    @ApiOperation(value = "添加课程界面")
    public void addCoursePage(){}

    @RequestMapping(value = "/courseToExamine", method = RequestMethod.POST)
    @ApiOperation(value = "增加新课程")
    public String addNewCourse(@ApiParam(value = "老师姓名") String name, int number, String courseName, String property, int credit, String classroom, int year, int term, int week, String time, String content, Model model){
        teacherService.addNewCourse(name, number, courseName, property, credit, classroom, year, term, week, time, content);
        return myCoursesPage(number, year, term, model);
    }

    @RequestMapping(value = "/getAllCourses", method = RequestMethod.POST)
    @ApiOperation(value = "发布成绩界面")
    public String releaseScorePage(int number, int year, int term, Model model){
        List<CourseBean> allCourses = teacherService.getCourseHasOpened(number, year, term);
        List<List<String>> res = new ArrayList<>();
        for(CourseBean cb : allCourses){
            List<String> list = new ArrayList<>();
            list.add(String.valueOf(cb.getId()));
            list.add(cb.getCourseName());
            list.add(cb.getProperty());
            list.add(String.valueOf(cb.getCredit()));
            list.add(weekToString(cb.getWeek())+cb.getTime());
            String state = teacherService.getScoreState(cb.getId());
            list.add(state);
            res.add(list);
        }
        model.addAttribute("allCourses", res);
        return "releaseScore";
    }

    @RequestMapping(value = "/releaseAndModify", method = RequestMethod.POST)
    @ApiOperation(value = "发布或修改界面")
    public String releasePage(@ApiParam(value = "课程id") int id, Model model){//发布界面，只返回了学生姓名和学号，未返回成绩、等第、绩点、备注
        List<StudentBean> allStudents = teacherService.allStudentsOfCourse(id);
        List<List<String>> students = new ArrayList<>();
        for(StudentBean sb : allStudents){
            if(sb.getGrade()!=null && sb.getGpa()!=null) continue;
            List<String> list = new ArrayList<>();
            list.add(sb.getName());
            list.add(String.valueOf(sb.getNumber()));
            students.add(list);
        }
        model.addAttribute("allStudents", students);
        return "release";
    }

    @RequestMapping(value = "/releaseScore", method = RequestMethod.POST)
    @ApiOperation(value = "给学生成绩")
    public String giveScore(@ApiParam(value = "学生学号") int number, int id, int score, String grade, String gpa, String comment, Model model){
        teacherService.giveScoreToStudent(number, id, score, grade, gpa, comment);
        return releasePage(id, model);
    }

    @RequestMapping(value = "/commitScore", method = RequestMethod.POST)
    @ApiOperation(value = "成绩提交审核")
    public String scoreToExamine(int id, @ApiParam(value = "老师工号") int number, int year, int term, Model model){
        teacherService.commitScoreToExamine(id);
        return releaseScorePage(number, year, term, model);
    }

    @RequestMapping(value = "/checkScore", method = RequestMethod.POST)
    @ApiOperation(value = "查看成绩")
    public String checkPage(int id, Model model){
        List<StudentBean> allStudents = teacherService.allStudentsOfCourse(id);
        List<List<String>> students = new ArrayList<>();
        for(StudentBean sb : allStudents){
            List<String> list = new ArrayList<>();
            list.add(sb.getName());
            list.add(String.valueOf(sb.getNumber()));
            list.add(String.valueOf(sb.getScore()));
            list.add(sb.getGrade());
            list.add(sb.getGpa());
            list.add(sb.getComment());
            students.add(list);
        }
        model.addAttribute("studentsScore", students);
        return "check";
    }
}
