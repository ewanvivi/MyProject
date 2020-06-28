package com.xjy.graduateweb.bean;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Teacher {
    private Integer id;
    private Integer teacherId;
    private String teacherName;
    private String teacherCategory;
    private String teacherLoginaccount;
}
