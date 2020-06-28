package com.xjy.graduateweb;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class GraduatewebApplicationTests {

    @Test
    void contextLoads() {
        Date date = new Date();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simple.format(date);
        System.out.println(simple.toString());
    }

}
