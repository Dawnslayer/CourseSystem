package com.program.senior.coursesystem.controller;

import com.program.senior.coursesystem.entity.UserBean;
import com.program.senior.coursesystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Api(value = "用户控制器", tags = {"用户"})
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/systemLogin", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录")
    public String login(String name, int number, String password, Model model){
        UserBean userBean = userService.loginIn(name, number, password);
        if(userBean==null) return "index";
        model.addAttribute("name", userBean.getName());
        model.addAttribute("number", userBean.getNumber());
        String type = userBean.getType();
        switch (type) {
            case "student":
                return "student";
            case "teacher":
                return "teacher";
            case "admin":
                return "admin";
            default:
                return "index";
        }
    }

    @RequestMapping(value = "/addNewUser", method = RequestMethod.POST)
    @ApiOperation(value = "新用户注册")
    public String register(String name, int number, String password, String type, Model model){
        int res = userService.register(name, number, password, type);
        if(res==0) return "register";
        return login(name, number, password, model);
    }

    @RequestMapping("/login")
    @ApiOperation(value = "进入登录界面")
    public void loginPage(){}

    @RequestMapping("/register")
    @ApiOperation(value = "进入注册界面")
    public void registerPage(){}
}
