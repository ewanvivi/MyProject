package com.xjy.graduateweb.controller;

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
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    AllService allService;

    @Autowired
    UserService userService;


    /**
     * @Author:Xjy
     * @Date:2020/06/28 分页
     */
    @RequestMapping("/page/{temp}")
    public String ManagerPage(
                            @PathVariable("temp") String temp,
                            @RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user != null) {
            //如果传入的各个页面不会空不为空
            if (temp.equals("info")) {
                Integer pageSize = 2;
                PageHelper.startPage(pn, pageSize);
                //根据点击的专业名称查询所有的论文
                List<OverPaper> list = allService.selectAll();
                for (OverPaper item : list) {
                    //每一个论文进行遍历,并且从数据库中获取被观看的总数
                    Integer integer = allService.selectAllPaperSumSee(item.getId());
                    item.setSumSee(integer);
                }
                PageInfo pageInfo = new PageInfo(list, 3);
                model.addAttribute("pageinfo", pageInfo);
                return "admin/adminmanager";

            } else if (temp.equals("showAudit")) {
                Integer pageSize = 2;
                PageHelper.startPage(pn, pageSize);
                Teacher teacher = allService.selectTeacherAuditByUserId(user.getTeacherId());
                List<Audit> list = allService.selectAuditByTeacherIdAndName(teacher.getTeacherId(), teacher.getTeacherName(), 0);
                PageInfo pageInfo = new PageInfo(list, 3);
                model.addAttribute("pageinfo", pageInfo);
                return "/admin/audit";
            } else if (temp.equals("access")) {
                Teacher teacher = allService.selectTeacherAuditByUserId(user.getTeacherId());
                List<Audit> audits = allService.selectAuditByTeacherIdAndName(teacher.getTeacherId(), teacher.getTeacherName(), 1);
                Integer pageSize = 2;
                PageHelper.startPage(pn, pageSize);
                PageInfo pageInfo = new PageInfo(audits, 3);
                model.addAttribute("audits", pageInfo);
                return "/admin/access";
            }
        } else {
            return "redirect:/homepage";
        }
        return "redirect:/homepage";


    }


    /**
     * @Author:Xjy
     * @Date:2020/06/09 审核通过或者不通过
     */
    @ResponseBody
    @GetMapping("/accessOrdelete")
    public JsonMessage accessOrdelete(@RequestParam(value = "accessId", required = false) Integer accessId,
                                      @RequestParam(value = "deleteId", required = false) Integer deleteId,
                                      @RequestParam(value = "score", required = false) Integer score) {
        Audit audit = null;
        if (accessId != null) {
            audit = allService.selectAuditById(accessId);
            if (audit != null) {
                if (audit != null) {
                    //申报中
                    if (audit.getStatu() == 0) {
                        //通过了
                        allService.updateStatuByAuditId(audit.getStatu() + 1, audit.getId());
                        return JsonMessage.success();
                    } else if (audit.getStatu() == 1) {
                        OverPaper overPaper = new OverPaper();
                        overPaper.setCategory(audit.getCategory());
                        overPaper.setCreateId(audit.getUserid());
                        overPaper.setCreateName(audit.getUsername());
                        overPaper.setCreateTitle(audit.getTitle());
                        overPaper.setContent(audit.getContent());
                        overPaper.setIntro(audit.getIntro());
                        overPaper.setCreatetime(audit.getCreatetime());
                        overPaper.setUpdatetime(audit.getCreatetime());
                        overPaper.setGrade(audit.getGrade());
                        overPaper.setTeacherName(audit.getTeacherName());
                        overPaper.setImage(audit.getImage());
                        overPaper.setGrade(score);
                        //论文审核通过
                        allService.insertOverpaperById(overPaper);
                        //通过后需要删除未审核中的数据
                        allService.deleteAuditById(accessId);
                        return JsonMessage.success();
                    }

                } else {
                    return JsonMessage.fail();
                }
            }
        }
        if (deleteId != null) {
            audit = allService.selectAuditById(deleteId);
            if (audit != null) {
                if (audit.getStatu() == 0) {
                    //审核不通过直接删除
                    allService.deleteAuditById(deleteId);
                    return JsonMessage.success();
                } else if (audit.getStatu() == 1) {
                    allService.updateStatuByAuditId(audit.getStatu() - 1, audit.getId());
                    return JsonMessage.success();
                }
            } else {
                return JsonMessage.fail();
            }
        }

        return JsonMessage.fail();
    }


    /**
     * @Author:Xjy
     * @Date:2020/06/09 点击查看论文, 可以选择审核通过或者不通过
     */
    @GetMapping("/gotoAuditGraduate")
    public String gotoAuditGraduate(@RequestParam("id") Integer auditId, @RequestParam("userid") Integer userid, Model model) {
        //根据这个论文id查询出是谁发表的
        User user = allService.selectUserByAuditIdAndUserId(auditId, userid);
        IsVisible isVisible = allService.NewselectIsVisibleByUserId(userid);
        if (isVisible != null) {
            if (isVisible.getQqisvisible() == null) {
                isVisible.setQqisvisible(0);
            }
            if (isVisible.getPhoneisvisible() == null) {
                isVisible.setPhoneisvisible(0);
            }
            user.setQqisvisible(isVisible.getQqisvisible());
            user.setPhoneisvisible(isVisible.getPhoneisvisible());
        }


        //根据auditId查询出待审核内容
        Audit audit = allService.selectAuditById(auditId);
        model.addAttribute("userinfo", user);
        model.addAttribute("audit", audit);
        System.out.println(user);
        return "advices/content";
    }

    /**
     * @Author:Xjy
     * @Date:2020/06/09 查看所有待审核的论文
     */
    @GetMapping("/access")
    public String Declare(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Teacher teacher = allService.selectTeacherAuditByUserId(user.getTeacherId());
            List<Audit> audits = allService.selectAuditByTeacherIdAndName(teacher.getTeacherId(), teacher.getTeacherName(), 1);
            model.addAttribute("audits", audits);
            return "/admin/access";
        } else {
            return "redirect:/homepage";
        }
    }

    /**
     * @Author:Xjy
     * @Date:2020/06/09 查看所有待申报的论文
     */
    @GetMapping("/showAudit")
    public String Audit(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Teacher teacher = allService.selectTeacherAuditByUserId(user.getTeacherId());
            if (user.getLoginaccount().equals(teacher.getTeacherLoginaccount()) && user.getId() == teacher.getTeacherId()) {
                List<Audit> audits = allService.selectAuditByTeacherIdAndName(teacher.getTeacherId(), teacher.getTeacherName(), 0);
                //List<Audit> list = allService.selectNoAudit();
                model.addAttribute("audits", audits);
                return "/admin/audit";
            } else {
                return "redirect:/homepage";
            }

        } else {
            return "redirect:/homepage";
        }

    }

    /**
     * @Author:Xjy
     * @Date:2020/06/09 管理首页, 查看各种论文的详细信息
     */
    @GetMapping("/info")
    public String gotoInfo(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/homepage";
        }
        if (user.getStatu() == 1) {
            List<OverPaper> list = allService.selectAll();
            for (OverPaper item : list) {
                //每一个论文进行遍历,并且从数据库中获取被观看的总数
                Integer integer = allService.selectAllPaperSumSee(item.getId());
                item.setSumSee(integer);
            }
            model.addAttribute("list", list);
            return "admin/adminmanager";
        } else {
            return "manager/homepage";
        }

    }

}
