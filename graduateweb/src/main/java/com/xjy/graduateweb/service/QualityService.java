package com.xjy.graduateweb.service;

import com.xjy.graduateweb.bean.Menu;
import com.xjy.graduateweb.bean.Teacher;

import java.util.List;

public interface QualityService {


    List<Menu> getAllSecondaryMenu(String pid);

    List<Teacher> selectTeacherByCategory(String category);
}
