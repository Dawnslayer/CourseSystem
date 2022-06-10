package com.program.senior.coursesystem.controller;

import com.program.senior.coursesystem.entity.CourseBean;
import com.program.senior.coursesystem.entity.StudentBean;
import com.program.senior.coursesystem.service.AdminService;
import com.program.senior.coursesystem.service.StudentService;
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
@Api(value = "学生控制器", tags = {"学生"})
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private AdminService adminService;

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

    @RequestMapping(value = "/courseInfo", method = RequestMethod.POST)
    @ApiOperation(value = "选课界面")
    public String chooseCoursePage(@ApiParam(value = "学号") int number, int year, int term, Model model){
        List<CourseBean> canChoose = studentService.courseCanChoose(number, year, term);
        List<CourseBean> chosen = studentService.courseChosen(number, year, term);
        List<List<String>> canChooseResult = new ArrayList<>();
        List<List<String>> chosenResult = new ArrayList<>();
        for(CourseBean cb : canChoose){
            List<String> list = new ArrayList<>();
            list.add(String.valueOf(cb.getId()));
            list.add(cb.getCourseName());
            list.add(cb.getProperty());
            list.add(String.valueOf(cb.getCredit()));
            list.add(weekToString(cb.getWeek())+cb.getTime());
            list.add(String.valueOf(cb.getYear()));
            list.add(String.valueOf(cb.getTerm()));
            list.add(cb.getClassroom());
            list.add(cb.getTeacher());
            canChooseResult.add(list);
        }
        for(CourseBean cb : chosen){
            List<String> list = new ArrayList<>();
            list.add(String.valueOf(cb.getId()));
            list.add(cb.getCourseName());
            list.add(cb.getProperty());
            list.add(String.valueOf(cb.getCredit()));
            list.add(weekToString(cb.getWeek())+cb.getTime());
            list.add(String.valueOf(cb.getYear()));
            list.add(String.valueOf(cb.getTerm()));
            list.add(cb.getClassroom());
            list.add(cb.getTeacher());
            chosenResult.add(list);
        }
        model.addAttribute("courseCanChoose", canChooseResult);
        model.addAttribute("courseChosen", chosenResult);
        return "chooseCourse";
    }

    @RequestMapping(value = "/choose", method = RequestMethod.POST)
    @ApiOperation(value = "选课")
    public String choose(@ApiParam(value = "学生姓名") String name, int number, int id, int year, int term, Model model){
        studentService.chooseCourse(name, number, id);
        return chooseCoursePage(number, year, term, model);
    }

    @RequestMapping(value = "/dropCourse", method = RequestMethod.POST)
    @ApiOperation(value = "退课")
    public String dropCourse(@ApiParam(value = "课程id") int id, int number, int year, int term, Model model){
        studentService.dropCourse(id, number);
        return chooseCoursePage(number, year, term, model);
    }

    @RequestMapping(value = "/getCourseScore", method = RequestMethod.POST)
    @ApiOperation(value = "查看成绩界面")
    public String courseLearned(int number, int year, int term, Model model){
        List<List<String>> learnedCourse = new ArrayList<>();
        List<StudentBean> score = studentService.getCourseScore(number, year, term);
        for(StudentBean sb : score){
            List<String> list = new ArrayList<>();
            CourseBean cb = adminService.getCourseInfo(sb.getId());
            list.add(String.valueOf(sb.getId()));
            list.add(cb.getCourseName());
            list.add(cb.getProperty());
            list.add(String.valueOf(cb.getCredit()));
            list.add(cb.getTeacher());
            String state = teacherService.getScoreState(sb.getId());
            if(state.equals("已发布")){
                list.add(String.valueOf(sb.getScore()));
                list.add(sb.getGrade());
                list.add(sb.getGpa());
            }
            else {
                list.add("未发布");
                list.add("未发布");
                list.add("未发布");
            }
            learnedCourse.add(list);
        }
        model.addAttribute("courseLearned", learnedCourse);
        return "viewScore";
    }

    @RequestMapping(value = "/getTimetable", method = RequestMethod.POST)
    @ApiOperation(value = "课程表")
    public String myTimetable(int number, int year, int term, Model model){
        List<String>[] timetable = new ArrayList[11];
        for(int i=0;i<11;i++) {
            timetable[i] = new ArrayList<>();
            for(int j=0;j<7;j++) timetable[i].add("");
        }
        List<CourseBean> chosen = studentService.courseChosen(number, year, term);
        for(CourseBean cb : chosen){
            String[] time = cb.getTime().split("-");
            int week = cb.getWeek()-1;
            for(int i=timeIndex(time[0]); i<=timeIndex(time[1]); i++){
                if(i==-1) break;
                timetable[i].set(week, cb.getCourseName()+" "+cb.getClassroom());//课程表中目前信息：课名 教室
            }
        }
        model.addAttribute("timetable", timetable);
        return "timetable";
    }

    private int timeIndex(String time){
        switch (time){
            case "08:00": case "08:45":
                return 0;
            case "08:50": case "09:35":
                return 1;
            case "09:50": case "10:35":
                return 2;
            case "10:40": case "11:25":
                return 3;
            case "13:00": case "13:45":
                return 4;
            case "13:50": case "14:35":
                return 5;
            case "14:50": case "15:35":
                return 6;
            case "15:40": case "16:25":
                return 7;
            case "18:00": case "18:45":
                return 8;
            case "18:50": case "19:35":
                return 9;
            case "19:50": case "20:35":
                return 10;
            default:
                return -1;
        }
    }
}
