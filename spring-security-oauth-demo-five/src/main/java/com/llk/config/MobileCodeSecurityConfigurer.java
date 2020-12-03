package com.llk.config;

import com.llk.filter.MobileCodeAuthenticationFilter;
import com.llk.handler.LoginFailureHandler;
import com.llk.handler.LoginSuccessHandler;
import com.llk.provider.MobileCodeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;


@Configuration
public class MobileCodeSecurityConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private MobileCodeAuthenticationProvider mobileCodeAuthenticationProvider;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //创建我们的认证过滤器
        MobileCodeAuthenticationFilter mobileCodeAuthenticationFilter = new MobileCodeAuthenticationFilter();
        //给认证过滤器设置认证管理器
        mobileCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        //设置登录成功或失败后的处理器
        mobileCodeAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        mobileCodeAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler);

        //设置 session 管理策略，如果不设置的话就是默认的 NullAuthenticatedSessionStrategy
        SessionAuthenticationStrategy sessionAuthenticationStrategy = http
                .getSharedObject(SessionAuthenticationStrategy.class);
        if (sessionAuthenticationStrategy != null) {
            mobileCodeAuthenticationFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
        }

        //添加我们的 mobileCodeAuthenticationProvider 到 authenticationProviders 集合中
        http.authenticationProvider(mobileCodeAuthenticationProvider)
                //将认证过滤器加到过滤器链中
                .addFilterAfter(mobileCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
