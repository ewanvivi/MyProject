package com.xjy.graduateweb.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xjy.graduateweb.bean.*;
import com.xjy.graduateweb.mapper.UserMapper;
import com.xjy.graduateweb.service.AllService;
import com.xjy.graduateweb.service.UserService;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AllService allService;

    /**
     *@Author:Xjy
     *@Date:2020/06/16
     *用户修改图像
    */
    @ResponseBody
    @PostMapping("/fileUpload")
    public JsonMessage fileupload(@RequestParam("fileName") MultipartFile file,HttpSession session){
        User user = (User)session.getAttribute("user");

        String filename = file.getOriginalFilename();
        long size = file.getSize();
        System.out.println("文件的长度是:"+size);

        String path="D:\\IDEA\\IntelliJ IDEA 2019.3.1\\mywork\\project\\school\\graduateweb\\src\\main\\resources\\static\\guraduateImage";
        String uuid = UUID.randomUUID().toString();
        String temp = "/"+filename+uuid;
        File dest = new File(path+temp);
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            temp="/guraduateImage"+temp;
            System.out.println(temp);
            userService.updateUserImage(user.getId(),temp);
            return JsonMessage.success();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return JsonMessage.fail();
        } catch (IOException e) {
            e.printStackTrace();
            return JsonMessage.fail();
        }

    }

    /**
     *@Author:Xjy
     *@Date:2020/06/16
     *用户取消收藏论文
    */
    @ResponseBody
    @GetMapping("/unFavorite")
    public JsonMessage unFavorite(@RequestParam("userId")Integer userId,
                                   @RequestParam("overpaperId")Integer overpaperId){
        if (userId!=null&&overpaperId!=null){
            userService.unFavorite(userId,overpaperId);
            return JsonMessage.success();
        }else{
            return JsonMessage.fail();
        }

    }


    /**
     *@Author:Xjy
     *@Date:2020/06/16
     *用户收藏论文
    */
    @ResponseBody
    @PostMapping("/addFavorite")
    public JsonMessage addFavorite(@RequestParam("userId")Integer userId,
                                   @RequestParam("overpaperId")Integer overpaperId){
        if (userId!=null&&overpaperId!=null){
            userService.addFavorite(userId,overpaperId);
            return JsonMessage.success();
        }else{
            return JsonMessage.fail();
        }

    }



    /**
     *@Author:Xjy
     *@Date:2020/06/11
     *发表的论文
    */
    @GetMapping("/gotoAccessGraduate")
    public String gotoAccessGraduate(HttpSession session,Model model,@RequestParam(value = "pn",defaultValue = "1")Integer pn){
        User user = (User)session.getAttribute("user");
        if (user!=null){
            //通过id查询已经发表的论文
            List<OverPaper> overPapers = userService.selectAccessGuraduateByUserId(user.getId());
            if (overPapers.size()==0){
                model.addAttribute("msg","您没有发表的论文");
                return "user/access";
            }else {
                Teacher teacher = allService.selectTeacherNameByOverpaperTeacherId(overPapers.get(0).getTeacherId());

                for (OverPaper overPaper:overPapers){
                    overPaper.setTeacherName(teacher.getTeacherName());
                    //根据论文id获取user
                    User userById = userService.selectUserById(overPaper.getCreateId());
                    //通过userid获取该论文被看过多少次
                    Integer seeCount = userService.selectSumSeeByOverpaperId(overPaper.getId());
                    //被收藏多少次
                    Integer likeCount = userService.selectLike(overPaper.getId());
                    //作者的等级
                    Level level = userService.selectLevelByOverpaperId(overPaper.getCreateId());
                    overPaper.setLevelName(level.getLevelName());
                    overPaper.setUserImage(userById.getImage());
                    overPaper.setSeeCount(seeCount);
                    overPaper.setLikeCount(likeCount);
                }
                Integer pageSize=5;
                PageHelper.startPage(pn, pageSize);
                PageInfo pageInfo = new PageInfo(overPapers,5);
                model.addAttribute("accessOverpapers",pageInfo);

                List<OverPaper> temp = userService.selectAccessGuraduateByUserId(user.getId());
                List<OverPaper> randomThree = new ArrayList<>();
                //添加之后在随机出五条展示在右侧
                for (int i = 0; i <=5; i++) {
                    if (temp.size()!=0){
                        int num = (int)(Math.random()*temp.size());
                        randomThree.add(temp.get(num));
                        temp.remove(num);
                    }else{
                        break;
                    }
                }
                model.addAttribute("randomoverPapers",randomThree);
                return "user/access";
            }
        }else{
            return "redirect:/homepage";
        }
    }


    /**
     *@Author:Xjy
     *@Date:2020/06/11
     *去到收藏页面
     */
    @GetMapping("/gotoEnshrine")
    public String gotoEnshrine(HttpSession session,Model model,@RequestParam(value = "pn",defaultValue = "1")Integer pn){
        User user = (User)session.getAttribute("user");
        if (user!=null){
            Integer pageSize=2;
            PageHelper.startPage(pn, pageSize);
            //查询出user收藏所有的论文

            List<OverPaper> overPapers = userService.selectEnshrineById(user.getId());
            if (overPapers.size()==0||overPapers==null){
                model.addAttribute("msg","您没有收藏的论文");
                return "user/enshrine";
            }else {
                for (OverPaper overPaper:overPapers){
                    //根据论文id获取user
                    User userById = userService.selectUserById(overPaper.getCreateId());
                    //通过userid获取该论文被看过多少次
                    Integer seeCount = userService.selectSumSeeByOverpaperId(overPaper.getId());
                    //被收藏多少次
                    Integer likeCount = userService.selectLike(overPaper.getId());
                    //作者的等级
                    Level level = userService.selectLevelByOverpaperId(overPaper.getCreateId());
                    overPaper.setLevelName(level.getLevelName());
                    overPaper.setUserImage(userById.getImage());
                    overPaper.setSeeCount(seeCount);
                    overPaper.setLikeCount(likeCount);
                }
                PageInfo pageInfo = new PageInfo(overPapers,2);
                model.addAttribute("overPapers",pageInfo);
                List<OverPaper> temp = userService.selectEnshrineById(user.getId());
                List<OverPaper> randomThree = new ArrayList<>();
                //添加之后在随机出五条展示在右侧
                for (int i = 0; i <=5; i++) {
                    if (temp.size()!=0){
                        int num = (int)(Math.random()*temp.size());
                        randomThree.add(temp.get(num));
                        temp.remove(num);
                    }else{
                        break;
                    }
                }
                model.addAttribute("randomoverPapers",randomThree);
                return "user/enshrine";
            }
        }else{
            return "redirect:/homepage";
        }

    }


    /**
     *@Author:Xjy
     *@Date:2020/06/08
     *用户退出
    */
    @GetMapping("/loginout")
    public String loginout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().invalidate();
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie cookie:cookies){
                if (cookie.getName().equals("token")){
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
        return "redirect:/homepage";
    }


    /**
     *@Author:Xjy
     *@Date:2020/06/08
     * 修改个人信息
    */
    @ResponseBody
    @GetMapping("/editUserinfo")
    public JsonMessage editUserINfo(User user,HttpSession session,HttpServletRequest request){
        if (user!=null){
            Date date = new Date();
            SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = simple.format(date);
            user.setUpdatetime(format);

            try {
                userService.editUserInfo(user);
                userService.editUserVisible(user.getPhoneisvisible(),user.getQqisvisible(),user.getId());
                return JsonMessage.success();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            return JsonMessage.fail();
        }
        return null;
    }


    /**
     *@Author:Xjy
     *@Date:2020/06/07
     *去到修改个人信息
    */
    @GetMapping("/gotoEditUserinfo")
    public String gotoEditPage(){
        return "user/editInfo";
    }



    /**
     *@Author:Xjy
     *@Date:2020/06/07
     *去到个人信息
    */
    @RequestMapping("/userinfo")
    public String userinfo(@RequestParam("id") Integer id, HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        if (user!=null){
            if (id!=null){
                return "user/index";
            }else{
                return "userlogin/login";
            }
        }else{
            return "redirect:/homepage";
        }

    }

    /**
     * @Author:Xjy
     * @Date:2020/06/07 去登陆页面
     */
    @GetMapping("/gotoLogin")
    public String gotoLogin() {
        return "userlogin/login";
    }

    /**
     * @Author:Xjy
     * @Date:2020/06/07 去注册页面
     */
    @GetMapping("/gotoRegister")
    public String gotoRegister() {
        return "userlogin/register";
    }

    /**
     * @Author:Xjy
     * @Date:2020/06/07 ajax查询登陆名称是否重复
     */
    @ResponseBody
    @GetMapping("/repetition")
    public JsonMessage isRepetition(@RequestParam("loginaccount") String loginaccount) {
        String flag = userService.isRepetition(loginaccount);
        System.out.println(flag);
        if (StringUtils.isEmpty(flag)) {
            return JsonMessage.success().add("message", "没有该登陆账户");
        } else {
            return JsonMessage.fail().add("message", "该登陆账户已重复");
        }

    }

    /**
     * @Author:Xjy
     * @Date:2020/06/07 注册页面, post请求
     */
    @ResponseBody
    @PostMapping("/register")
    public JsonMessage register(User temp,@RequestParam("category")String category,
                                @RequestParam("teacherName")String teacherName,
            HttpSession session) {
        Teacher t = userService.selectTeacherByCategoryAndTeacherName(category,teacherName);
        User user = new User();
        user.setLoginaccount(temp.getLoginaccount());
        user.setPassword(temp.getPassword());
        user.setNickname(temp.getNickname());
        Date date = new Date();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simple.format(date);
        user.setCreatetime(format);
        user.setUpdatetime(format);
        user.setStatu(0);
        user.setWorkposition("未知");
        user.setTeacherId(t.getTeacherId());
        user.setLevel(1);
        user.setImage("/portal/images/desktop2.jpg");
        userService.insertUser(user);
        Integer id = userService.selectUserByLoginaccount(user.getLoginaccount());
        user.setId(id);
        session.setAttribute("user",user);

        return JsonMessage.success();
    }

    /**
     * @Author:Xjy
     * @Date:2020/06/07 提交登陆请求
     */
    @ResponseBody
    @GetMapping("/login")
    public JsonMessage userInfo(String loginaccount, String password, HttpSession session,
                                @RequestParam(value="remember",required = false) Integer remember, HttpServletResponse response, HttpServletRequest request) {
        User user = userService.selectByLoginaccountAndPassword(loginaccount, password);
        String token = UUID.randomUUID().toString();
        if (user != null) {
            //记住我
            if (remember == 1) {   //有cookie的话就更新,没有的话就添加
                //设置cookie
                Cookie cookie = new Cookie("token", token);
                cookie.setMaxAge(7 * 24 * 60 * 60);
                cookie.setPath("/");
                response.addCookie(cookie);
                userService.insertUserCookie(token,user.getId());
                IsVisible isVisible = allService.NewselectIsVisibleByUserId(user.getId());
                user.setQqisvisible(isVisible.getQqisvisible());
                user.setPhoneisvisible(isVisible.getPhoneisvisible());
                session.setAttribute("user", user);
                return JsonMessage.success();
            } else {
                //没记住我,只放入session
                IsVisible isVisible = allService.NewselectIsVisibleByUserId(user.getId());
                user.setQqisvisible(isVisible.getQqisvisible());
                user.setPhoneisvisible(isVisible.getPhoneisvisible());
                session.setAttribute("user", user);
                return JsonMessage.success().add("user", user);
            }
        } else {
            return JsonMessage.fail().add("msg", "用户名或密码错误");
        }

    }

}
