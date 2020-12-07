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

    @RequestMapping("/get")
    public Book get(){
        Book book = new Book();
        book.setBookId("1");
        book.setBookName("《Thinking in Java》");
        book.setAuthor("Bruce Eckel");

        return book;
    }

    @RequestMapping("/delete")
    public String delete(){
        //模拟删除书籍的代码
        System.out.println("删除书籍");
        return "删除成功";
    }
}
