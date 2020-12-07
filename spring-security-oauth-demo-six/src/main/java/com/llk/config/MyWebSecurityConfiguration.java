package com.llk.config;

import com.llk.handler.LoginFailureHandler;
import com.llk.handler.LoginSuccessHandler;
import com.llk.strategy.SessionExpiredStrategy;
import com.llk.strategy.SessionInvalidStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        //内存用户管理器，该类最终也实现自 UserDetailsService
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("llk").password(passwordEncoder().encode("123")).authorities("USER").build());
        manager.createUser(User.withUsername("zhangsan").password(passwordEncoder().encode("123456")).authorities("USER","ADMIN").build());
        return manager;
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin()
                    .failureHandler(loginFailureHandler)
                    .successHandler(loginSuccessHandler)
                .and().sessionManagement()
                    .invalidSessionStrategy(new SessionInvalidStrategy())
                    .maximumSessions(1)//最多一个允许一个设备登录
                    //登录被顶掉时的处理逻辑
                    .expiredSessionStrategy(new SessionExpiredStrategy()).and()

                .and().authorizeRequests()
                    .antMatchers("/book/get/**").hasAnyAuthority("USER","ADMIN")
                    .antMatchers("/book/delete").hasAuthority("ADMIN")
                    .anyRequest().authenticated();
        ;
    }

}
