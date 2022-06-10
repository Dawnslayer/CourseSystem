package com.program.senior.coursesystem.controller;

import com.program.senior.coursesystem.entity.CourseBean;
import com.program.senior.coursesystem.entity.StudentBean;
import com.program.senior.coursesystem.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@Api(value = "教务控制器", tags = {"教务"})
public class AdminController {
    @Autowired
    private AdminService adminService;

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

    @RequestMapping(value = "/courseExamine", method = RequestMethod.POST)
    @ApiOperation(value = "审核课程界面")
    public String examineCoursePage(Model model){
        List<CourseBean> courseCanExamine = adminService.getCourseCanExamine();
        List<List<String>> res = new ArrayList<>();
        for(CourseBean cb : courseCanExamine){
            List<String> list = new ArrayList<>();
            list.add(String.valueOf(cb.getId()));
            list.add(cb.getCourseName());
            list.add(cb.getProperty());
            list.add(cb.getTeacher());
            res.add(list);
        }
        model.addAttribute("courseCanExamine", res);
        return "examineCourse";
    }

    @RequestMapping(value = "/detailInfo", method = RequestMethod.POST)
    @ApiOperation(value = "课程详细信息")
    public String examinePage(int id, Model model){
        CourseBean cb = adminService.getCourseInfo(id);
        model.addAttribute("courseName", cb.getCourseName());
        model.addAttribute("property", cb.getProperty());
        model.addAttribute("year", cb.getYear());
        model.addAttribute("term", cb.getTerm());
        model.addAttribute("time", weekToString(cb.getWeek())+cb.getTime());
        model.addAttribute("classroom", cb.getClassroom());
        model.addAttribute("teacher", cb.getTeacher());
        model.addAttribute("credit", cb.getCredit());
        model.addAttribute("content", cb.getContent());
        return "examine";
    }

    @RequestMapping(value = "/courseAccept", method = RequestMethod.POST)
    @ApiOperation(value = "课程审核通过")
    public String acceptCourse(int id, Model model){
        adminService.courseAccept(id);
        return examineCoursePage(model);
    }

    @RequestMapping(value = "/courseRefuse", method = RequestMethod.POST)
    @ApiOperation(value = "课程审核不通过")
    public String refuseCourse(int id, Model model){
        adminService.courseRefuse(id);
        return examineCoursePage(model);
    }

    @RequestMapping(value = "/scoreExamine", method = RequestMethod.POST)
    @ApiOperation(value = "审核成绩界面")
    public String examineScorePage(Model model){
        List<CourseBean> scoreCanExamine = adminService.getScoreCanExamine();
        List<List<String>> res = new ArrayList<>();
        for(CourseBean cb : scoreCanExamine){
            List<String> list = new ArrayList<>();
            list.add(String.valueOf(cb.getId()));
            list.add(cb.getCourseName());
            list.add(cb.getProperty());
            list.add(String.valueOf(cb.getYear()));
            list.add(String.valueOf(cb.getTerm()));
            list.add(weekToString(cb.getWeek())+cb.getTime());
            list.add(String.valueOf(cb.getCredit()));
            list.add(cb.getTeacher());
            res.add(list);
        }
        model.addAttribute("scoreCanExamine", res);
        return "examineScore";
    }

    @RequestMapping(value = "/allStudentScore", method = RequestMethod.POST)
    @ApiOperation(value = "所有学生成绩")
    public String studentScorePage(int id, Model model){
        List<StudentBean> students = adminService.getStudentScore(id);
        List<List<String>> allScore = new ArrayList<>();
        for(StudentBean sb : students){
            List<String> list = new ArrayList<>();
            list.add(sb.getName());
            list.add(String.valueOf(sb.getNumber()));
            list.add(String.valueOf(sb.getScore()));
            list.add(sb.getGrade());
            list.add(sb.getGpa());
            list.add(sb.getComment());
            allScore.add(list);
        }
        model.addAttribute("scoreOfStudents", allScore);
        return "studentScore";
    }

    @RequestMapping(value = "/scoreAccept", method = RequestMethod.POST)
    @ApiOperation(value = "成绩审核通过")
    public String acceptScore(int id, Model model){
        adminService.scoreAccept(id);
        return examineScorePage(model);
    }

    @RequestMapping(value = "/scoreRefuse", method = RequestMethod.POST)
    @ApiOperation(value = "成绩审核不通过")
    public String refuseScore(int id, Model model){
        adminService.scoreRefuse(id);
        return examineScorePage(model);
    }
}
