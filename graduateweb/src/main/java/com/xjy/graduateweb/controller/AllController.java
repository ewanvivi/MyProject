package com.xjy.graduateweb.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xjy.graduateweb.bean.*;
import com.xjy.graduateweb.service.AllService;
import com.xjy.graduateweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/all")
@Controller
public class AllController {

    @Autowired
    AllService allService;

    @Autowired
    UserService userService;

    /**
     *@Author:Xjy
     *@Date:2020/06/05
     *传过来的是论文的id
    */
    @GetMapping("/selectGraduationById")
    public String selectGraduation(@RequestParam("id")Integer paperId, Model model, HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        //根据论文的id查询出毕业论文的详细信息
        OverPaper overPaper = allService.selectOverpaperbyId(paperId);
        Integer createId = overPaper.getCreateId();
        User userinfo =null;
        if (createId!=null){
            //根据这个论文id查询出是谁发表的
            userinfo = allService.selectUserByPaperId(paperId,createId);
            Integer userid = userinfo.getId();
            //查询出是否可以观看
            IsVisible isVisible = allService.NewselectIsVisibleByUserId(userid);
            if (isVisible!=null){
                if (isVisible.getQqisvisible()==null){
                    isVisible.setQqisvisible(0);
                }
                if (isVisible.getPhoneisvisible()==null){
                    isVisible.setPhoneisvisible(0);
                }
                userinfo.setQqisvisible(isVisible.getQqisvisible());
                userinfo.setPhoneisvisible(isVisible.getPhoneisvisible());
            }
        }
        //未登录或者是游客
        if (user==null){
            model.addAttribute("overPaper",overPaper);
            model.addAttribute("userinfo",userinfo);
            return "advices/content";
        }else if(user.getId()!=null){
            //不是游客,已经登陆过了
            Integer userId = user.getId();
            See isSee = allService.selectIsSee(paperId,userId);
            if (isSee!=null) {
                Enshrine enshrine = userService.selectEnshrineByOverpaperIdAndUserId(user.getId(),paperId);
                model.addAttribute("enshrine",enshrine);
                //已经看过了就不用增加
                model.addAttribute("overPaper",overPaper);
                model.addAttribute("userinfo",userinfo);
                return "advices/content";
            }else{
                //没看过增加
                Enshrine enshrine = userService.selectEnshrineByOverpaperIdAndUserId(user.getId(),paperId);
                model.addAttribute("enshrine",enshrine);
                allService.insertUserSee(paperId,userId);
                model.addAttribute("overPaper",overPaper);
                model.addAttribute("userinfo",userinfo);
                return "advices/content";
            }
        }
        return null;
    }



//    @GetMapping("/major")
//    public String mytest(){
//
//    }


    /**
     *@Author:Xjy
     *@Date:2020/06/04
     *根据专业查询所有的论文
     */
    @ResponseBody
    @GetMapping("/major")
    public Object major(@RequestParam("category")String category,
                        @RequestParam(value = "pn",defaultValue = "1")Integer pn){
        //如果传入的专业不为空
        if (StringUtils.isEmpty(category)){
            return "faild";
        }else{
            Integer pageSize=8;
             PageHelper.startPage(1, pageSize);
            //根据点击的专业名称查询所有的论文
            List<OverPaper> list = allService.selectByCategory(category);
            for (OverPaper item:list){
                //每一个论文进行遍历,并且从数据库中获取被观看的总数
                Integer integer = allService.selectAllPaperSumSee(item.getId());
                item.setSumSee(integer);
            }
            PageInfo pageInfo = new PageInfo(list,5);
            System.out.println(pageInfo);
            return pageInfo;
        }
    }


}
