package com.llk.service;

import com.llk.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class AuthUserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {

        //此处模拟从数据库等中获取一个用户信息
        User user = getUser(mobile);

        return user;
    }

    private User getUser(String mobile){
        User user = new User();
        user.setMobile(mobile);
        user.setPassword("123123123123123");
        user.setUsername("com/llk");

        //模拟用户具有的权限
        GrantedAuthority grantedAuthority = new GrantedAuthority(){

            @Override
            public String getAuthority() {
                return "USER";
            }
        };

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(grantedAuthority);

        user.setAuthorities(authorityList);

        return user;
    }
}
