package com.llk.controller;


import com.llk.domain.Book;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/book")
public class BookController {

    //@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @RequestMapping("/get")
    public Book get(){
        Book book = new Book();
        book.setBookId("1");
        book.setBookName("《Thinking in Java》");
        book.setAuthor("Bruce Eckel");


        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取用户的权限，我们在 WebSecurityConfiguration 类中通过 authorities() 配置的值
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
        for(GrantedAuthority authority:authorities){
            System.out.println(authority.getAuthority());
        }

        //获取用户信息
        //这里的 User 类 是 org.springframework.security.core.userdetails 包中的
        User principal = (User)authentication.getPrincipal();
        String username = principal.getUsername();
        System.out.println("username:"+username);*/

        return book;
    }

    //@PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping("/delete")
    public String delete(){
        //模拟删除书籍的代码
        System.out.println("删除书籍");
        return "删除成功";
    }




    //获取用户信息测试
    @RequestMapping("/get/userDetails")
    public UserDetails getUser(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails;
    }

    @RequestMapping("/get/userAuthentication")
    public Authentication getUser(Authentication authentication){
        //SecurityContextHolder.getContext().getAuthentication() 获取的是同一个对象
        return authentication;
    }
}
