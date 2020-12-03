package com.llk.config;

import com.llk.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MobileCodeSecurityConfigurer mobileCodeSecurityConfigurer;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/smscode/send",//获取验证码的接口不需要登录
                        "/toLoginPage",//跳转到登录界面
                        "/mobile/login")//处理登录请求的接口不需要登录
                .permitAll()
                .antMatchers("/book/get/**").hasAnyAuthority("USER","ADMIN")
                .anyRequest().authenticated()  //其他任何请求都需要身份认证

                //将 mobileCodeSecurityConfigurer 设置给 httpSecurity
                .and().apply(mobileCodeSecurityConfigurer)

                .and().logout().permitAll()
                .and().csrf().disable();    //禁用CSRF

    }

    @Bean
    public UserDetailsService userDetailsService() {
        AuthUserService userService = new AuthUserService();
        return userService;
    }
}
