package com.xjy.graduateweb.bean;

import lombok.Data;
import lombok.ToString;


@ToString
@Data
public class Menu {
    private Integer id;
    private String name;
    private Integer pid;
}
