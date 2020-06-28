package com.xjy.graduateweb.controller;

import com.xjy.graduateweb.bean.JsonMessage;
import com.xjy.graduateweb.bean.OverPaper;
import com.xjy.graduateweb.bean.Menu;
import com.xjy.graduateweb.bean.Teacher;
import com.xjy.graduateweb.service.AllService;
import com.xjy.graduateweb.service.QualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/quality")
@RestController
public class QualityController {

    @Autowired
    QualityService qualityService;

    @Autowired
    AllService allService;

    /**
     *@Author:Xjy
     *@Date:2020/06/15
     *根据专业名称查出老师
    */
    @GetMapping("/selectTeacher")
    public JsonMessage chooseTeacher(@RequestParam("category")String category){
        if (category!=null){
            List<Teacher> teachers = qualityService.selectTeacherByCategory(category);
            List<String> list = new ArrayList<>();
            for (Teacher t:teachers){
                list.add(t.getTeacherName());
            }
            return JsonMessage.success().add("teacherNames",list);
        }else{
            return JsonMessage.fail();
        }
    }


    /**
     *@Author:Xjy
     *@Date:2020/06/10
     *前端二级联动
    */
    @GetMapping("/secondLinkage")
    public JsonMessage secondLinkage(@RequestParam(value="id",required = false)Integer parentId){
        //查询父级
        if (parentId==null){
            List<Menu> parent = allService.selectParentMenu();
            return  JsonMessage.success().add("parent",parent);
        }else{
            List<Menu> child = allService.selectSecondMenu(parentId);
            return  JsonMessage.success().add("child",child);
        }

    }


    /**
     *@Author:Xjy
     *@Date:2020/06/04
     *根据系部查询所有的专业
    */
    @GetMapping("/secondarymenu")
    public Object secondaryMenu(@RequestParam("pid") String pid){
        List<Menu> list =  qualityService.getAllSecondaryMenu(pid);
        return list;
    }
}
