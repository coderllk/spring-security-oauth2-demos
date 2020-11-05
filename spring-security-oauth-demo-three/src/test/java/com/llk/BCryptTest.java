package com.llk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BCryptTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void encode(){
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }
}
