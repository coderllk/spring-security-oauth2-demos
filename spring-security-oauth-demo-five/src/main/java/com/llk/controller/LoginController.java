package com.llk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/toLoginPage")
    public String toLoginPage(){
        return "myLoginPage";
    }
}
