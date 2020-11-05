package com.llk.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @RequestMapping("/toLoginPage")
    public String toLoginPage(){
        return "myLoginPage";
    }


    @ResponseBody
    @RequestMapping("/login/success")
    public String loginSuccess(){
        System.out.println("loginSuccess执行了");
        //在这里我们就可以做很多别的事情
        return "登录成功";
    }

    @ResponseBody
    @RequestMapping("/login/success2")
    public String loginSuccess2(){
        System.out.println("loginSuccess2执行了");
        return "登录成功2";
    }

    @ResponseBody
    @RequestMapping("/login/failure")
    public String loginFailure(){
        System.out.println("loginFailure 执行了");
        return "登录失败";
    }

    @ResponseBody
    @RequestMapping("/login/failure2")
    public String loginFailure2(){
        System.out.println("loginFailure2 执行了");
        return "登录失败2";
    }


}
