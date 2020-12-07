package com.llk.strategy;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 登录被顶替时执行
 */
public class SessionExpiredStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {

        event.getResponse().setContentType("application/json; charset=utf-8");
        event.getResponse().getWriter().write("你的账号已在别的设备登录，请确认或重新登录");
    }

}
