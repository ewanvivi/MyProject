package com.xjy.graduateweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class Mytest {

    @GetMapping("/file")
    public String mytest(){
        return "test/mytest";
    }

    @ResponseBody
    @PostMapping("/fileUpload")
    public String fileupload(@RequestParam("fileName") MultipartFile file){
        if (file.isEmpty()){
            return "test/mytest";
        }
        String filename = file.getOriginalFilename();
        long size = file.getSize();
        System.out.println("文件的长度是:"+size);

        String path="D:\\IDEA\\IntelliJ IDEA 2019.3.1\\mywork\\project\\school\\graduateweb\\src\\main\\resources\\static\\guraduateImage";
        File dest = new File(path+"/"+filename);
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            return "true";
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        }

    }

}
