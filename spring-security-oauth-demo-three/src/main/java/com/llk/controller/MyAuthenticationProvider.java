package com.llk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component//注册到容器中
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //authentication 其实也是一个 UsernamePasswordAuthenticationToken 对象
        System.out.println(authentication.getClass());
        //获取用户在登录界面输入的信息
        String userName = (String) authentication.getPrincipal(); //拿到username
        String password = (String) authentication.getCredentials(); //拿到password

        //在 WebSecurityConfiguration 中配置的用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        if(userDetails == null){
            throw new UsernameNotFoundException("not found this username");
        }

        if ( passwordEncoder.matches(password,userDetails.getPassword()) ) {//验证密码
            //验证成功将结果返回给 PrivoderManager
            return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
        }else{
            throw new BadCredentialsException("the password and the username are not matches");
        }
        //如果返回了null，AuthenticationManager会交给下一个支持 authentication类型的AuthenticationProvider处理。
        //return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //该 provider 支持的认证方式
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
