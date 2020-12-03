package com.llk.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class SmsController {

    Logger logger = LoggerFactory.getLogger(SmsController.class);

    public static final String SMS_CODE = "SMS_CODE";

    @RequestMapping("/smscode/send")
    public String sendSmsCode(HttpSession session, HttpServletRequest request) throws ServletRequestBindingException {
        String mobile = request.getParameter("mobile");

        //随机生成一个验证码
        Random rd=new Random();
        int code = rd.nextInt(10000);
        //模拟向用户发送短信
        logger.info("send code to "+mobile+" ："+code);

        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("code", code);

        //将验证码存到 session 中，后面验证的时候，从 session 中获取
        session.setAttribute(SMS_CODE,map);
        return "验证码发送成功，请注意查收";
    }
}
