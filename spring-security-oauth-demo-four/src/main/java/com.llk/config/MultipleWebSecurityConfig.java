package com.llk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
public class MultipleWebSecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //用户信息
    @Autowired
    protected void authUserInfo(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("llk")
                .password(passwordEncoder().encode("1234"))
                .authorities("USER")
                .and()
                .withUser("zhangsan")
                .password(passwordEncoder().encode("123456"))
                .authorities("USER","ADMIN");
    }

    @Order(2)
    @Configuration
    class CommonSecurityConfig extends WebSecurityConfigurerAdapter{

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/common/**")
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/common/book/get").hasAuthority("USER")
                    .anyRequest().authenticated();
            ;
        }
    }

    @Order(3)
    @Configuration
    class AdminSecurityConfig extends WebSecurityConfigurerAdapter{
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/admin/**")
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/admin/book/delete").hasAuthority("ADMIN")
                    .antMatchers("/admin/book/update").hasAnyAuthority("ADMIN")
                    .anyRequest().authenticated();
            ;
        }
    }

    @Order(1)
    @Configuration
    class OtherSecurityConfig extends WebSecurityConfigurerAdapter{

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .formLogin()
                    .permitAll()
                    .and()
                    .authorizeRequests()
                    .anyRequest().authenticated();
            ;
        }
    }
}
