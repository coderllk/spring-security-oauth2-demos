package com.llk.config;

import com.llk.controller.LoginFailureHandler;
import com.llk.controller.LoginSuccessHandler;
import com.llk.controller.MyAuthenticationProvider;
import com.llk.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;

    @Autowired
    private UserAuthService userAuthService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myAuthenticationProvider);
        auth.userDetailsService(userAuthService);
    }

    /*@Bean
    public UserDetailsService userDetailsService() {
        //内存用户管理器，该类最终也实现自 UserDetailsService
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("llk").password(passwordEncoder().encode("123")).authorities("USER").build());
        manager.createUser(User.withUsername("zhangsan").password(passwordEncoder().encode("123456")).authorities("USER","ADMIN").build());
        return manager;
    }*/



    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin()
                    //.usernameParameter("user")
                    //.passwordParameter("passwd")
                   .loginPage("/toLoginPage")//跳转到登录界面的接口
                    .loginProcessingUrl("/login")//如果不设置的话，就与 .loginPage() 设置的值相同
                    .successForwardUrl("/login/success2")

                    .failureHandler(loginFailureHandler)
                    /*.failureForwardUrl("/login/failure")
                    .failureUrl("/login/failure2")*/

                    /*.defaultSuccessUrl("/login/success",true)
                    .successHandler(loginSuccessHandler)
                    .defaultSuccessUrl("/login/success")
                    .successForwardUrl("/login/success2")*/
                .permitAll()
                .and()
                    .rememberMe()
                .and()
                    .authorizeRequests()
                    .antMatchers("/book/get/**").hasAnyAuthority("USER","ADMIN")
                    .antMatchers("/book/delete").hasAuthority("ADMIN")
                    .anyRequest().authenticated();
        ;
    }

}
