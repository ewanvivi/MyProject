package com.xjy.graduateweb.bean;

import lombok.Data;
import lombok.ToString;


@ToString
@Data
public class User {
    private Integer id;
    private String loginaccount;
    private String password;
    private String nickname;
    private String workposition;
    private Integer age;
    private String phone;
    private String qq;
    private String image;
    private String selfIntroduction;
    private String createtime;
    private String updatetime;
    private Integer statu;
    private String cookieCode;
    private Integer qqisvisible;
    private Integer phoneisvisible;
    private Integer teacherId;
    private Integer level;

}
