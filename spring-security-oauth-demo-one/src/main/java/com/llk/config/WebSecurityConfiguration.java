package com.llk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    //配置系统中的用户，就相当于这些用户已经完成注册了
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()//基于内存的方式，用户信息只保存在内存中，系统重启就没有了
                .withUser("llk")//用户名
                //模拟注册时密码用 BCrypt 算法进行加密
               .password(passwordEncoder().encode("123"))//密码
                .authorities("USER")//用户的权限。必须配置，不配置的话会报错：
                //Caused by: java.lang.IllegalArgumentException: Cannot pass a null GrantedAuthority collection
                .and()
                .withUser("zhangsan")
                .password(passwordEncoder().encode("123456"))
                //.password("{noop}123456")
                .authorities("USER","ADMIN");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                //.csrf().disable()
                .formLogin()
                .permitAll()
                .and()
                .rememberMe()
                .and()
                //.logout()
                //.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) 老版本需要配置
                //.and()
                .authorizeRequests()
                .antMatchers("/book/get/**").hasAnyAuthority("USER","ADMIN")
                .antMatchers("/book/delete").hasAuthority("ADMIN")
                .anyRequest().authenticated();
        ;
    }

}
