package com.xjy.graduateweb.controller;


import com.xjy.graduateweb.bean.JsonMessage;
import com.xjy.graduateweb.bean.OverPaper;
import com.xjy.graduateweb.bean.Teacher;
import com.xjy.graduateweb.bean.User;
import com.xjy.graduateweb.service.AllService;
import com.xjy.graduateweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
public class GraduationController {

    @Autowired
    AllService allService;

    @Autowired
    UserService userService;

    /**
     *@Author:Xjy
     *@Date:2020/06/10
     *提交过来的论文,放入到申报论文审核
    */
    @ResponseBody
    @PostMapping("/submitGraduate")
    public JsonMessage submitGraduate(OverPaper overPaper,@RequestParam("file") MultipartFile multipartFile){
        if (overPaper!=null){
            //申报论文审核
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date());
            overPaper.setCreatetime(format);
            //allService.selectTeacherByUserId();
            Teacher teacher = allService.selectTeacherNameByOverpaperTeacherId(overPaper.getTeacherId());
            overPaper.setTeacherName(teacher.getTeacherName());
            overPaper.setTeacherId(teacher.getTeacherId());


            String filename = multipartFile.getOriginalFilename();
            String path="D:\\IDEA\\IntelliJ IDEA 2019.3.1\\mywork\\project\\school\\graduateweb\\src\\main\\resources\\static\\guraduateImage";
            String uuid = UUID.randomUUID().toString();
            String temp = "/"+filename+uuid;
            File dest = new File(path+temp);
            if (!dest.getParentFile().exists()){
                dest.getParentFile().mkdir();
            }
            try {
                multipartFile.transferTo(dest); //保存文件
                temp="/guraduateImage"+temp;
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return JsonMessage.fail();
            } catch (IOException e) {
                e.printStackTrace();
                return JsonMessage.fail();
            }

            overPaper.setImage(temp);
            allService.insertOverpaperToAudit(overPaper);
            return JsonMessage.success();
        }else{
            return JsonMessage.fail();
        }
    }


    /**
     *@Author:Xjy
     *@Date:2020/06/09
     *通过校验了然后去撰写论文
    */
    @GetMapping("/gotowriteGraduate")
    public String gotoWriteGraduate(HttpSession session){
        User user = (User)session.getAttribute("user");
        if (user!=null){
            return "addGraduate/publish";
        }else{
            return "redirect:/homepage";
        }

    }

    /**
     *@Date:2020/06/08
     *是否可以编写论文
    */
    @ResponseBody
    @GetMapping("/gotoWrite")
    public JsonMessage gotoWrite(HttpSession session){
        User user = (User)session.getAttribute("user");
        if (user!=null){
            return JsonMessage.success();
        }else{
            return JsonMessage.fail();
        }
    }


    /**
     *@Author:Xjy
     *@Date:2020/06/07
     *来到毕业论文首页前校验是否登陆以及cookie是否存在
    */
    @RequestMapping("/homepage")
    public String hello2(HttpSession session, HttpServletRequest request, Model model){

        Cookie[] cookies = request.getCookies();
        if (cookies==null){
            return "manager/inbox";
        }else{
            for (Cookie cookie:cookies){
                if (cookie.getName().equals("token")){
                    String cookieValue = cookie.getValue();
                    User user = userService.getUserCookie(cookieValue);
                    if (cookieValue.equals(user.getCookieCode())){
                        session.setAttribute("user",user);
                        return "manager/inbox";
                    }
                }
            }
        }
        return "manager/inbox";
    }
}
