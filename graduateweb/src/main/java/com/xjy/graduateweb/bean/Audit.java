package com.xjy.graduateweb.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Audit {
    private Integer id;
    private String category;
    private Integer userid;
    private String username;
    private String title;
    private String intro;
    private String content;
    private String createtime;
    private Integer grade;
    private Integer statu;
    private String teacherName;
    private String image;
}
