package com.llk.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class User implements UserDetails {

    private Integer userId;
    private String yonghuming;
    private String mima;
    private int age;
    private String authority;

    //将我们的 权限字符串进行分割，并存到集合中，最后供权限验证 使用
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] authorities = this.authority.split(",");
        ArrayList<GrantedAuthority> list = new ArrayList();
        for(int i = 0;i<authorities.length;i++){
            int finalI = i;
            GrantedAuthority grantedAuthority = new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return authorities[finalI];
                }
            };
            list.add(grantedAuthority);
        }
        return list;
    }

    //将我们的 mima 转换成 AuthenticationProvider 能够认识的 password
    @Override
    public String getPassword() {
        return this.mima;
    }

    //将我们的 yonghuming 转换成 AuthenticationProvider 能够认识的 username
    @Override
    public String getUsername() {
        return this.yonghuming;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getYonghuming() {
        return yonghuming;
    }

    public void setYonghuming(String yonghuming) {
        this.yonghuming = yonghuming;
    }

    public String getMima() {
        return mima;
    }

    public void setMima(String mima) {
        this.mima = mima;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    //都返回 true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + yonghuming + '\'' +
                ", password='" + mima + '\'' +
                ", age=" + age +
                ", authority='" + authority + '\'' +
                '}';
    }
}
