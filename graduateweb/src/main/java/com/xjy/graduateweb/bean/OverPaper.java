package com.xjy.graduateweb.bean;

import lombok.Data;
import lombok.ToString;

@Data
public class OverPaper {
    private Integer id;
    private String category;
    private Integer createId;
    private String createName;
    private String createTitle;
    private String content;
    private String intro;
    private Integer sumSee;
    private String createtime;
    private String updatetime;
    private Integer grade;
    private Integer teacherId;
    private String teacherName;
    private String image;
    private String userImage;
    private Integer seeCount;
    private Integer likeCount;
    private String levelName;
}

