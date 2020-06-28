package com.xjy.graduateweb.service.impl;

import com.xjy.graduateweb.bean.*;
import com.xjy.graduateweb.mapper.UserMapper;
import com.xjy.graduateweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User selectByLoginaccountAndPassword(String loginaccount, String password) {
        User user = userMapper.selectByLoginaccountAndPassword(loginaccount,password);
        return user;
    }

    @Override
    public String isRepetition(String loginaccount) {
        String flag = userMapper.isRepetition(loginaccount);
        return flag;
    }

    @Override
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public void insertUserCookie(String token, Integer id) {
        userMapper.insertUserCookie(token,id);
    }

    @Override
    public User getUserCookie(String cookieValue) {
        User user = userMapper.getUserCookie(cookieValue);
        return user;
    }

    @Override
    public User selectUserById(Integer id) {

        return userMapper.selectUserById(id);
    }

    @Override
    public void editUserInfo(User user) {
         userMapper.editUserInfo(user);
    }

    @Override
    public Integer selectUserByLoginaccount(String loginaccount) {
        return userMapper.selectUserByLoginaccount(loginaccount);
    }

    @Override
    public List<OverPaper> selectEnshrineById(Integer id) {
        return  userMapper.selectEnshrineById(id);

    }

    @Override
    public Integer selectSumSeeByOverpaperId(Integer id) {
        return userMapper.selectSumSeeByOverpaperId(id);
    }

    @Override
    public Integer selectLike(Integer id) {
        return userMapper.selectLike(id);
    }

    @Override
    public List<OverPaper> selectAccessGuraduateByUserId(Integer id) {
        return userMapper.selectAccessGuraduateByUserId(id);
    }

    @Override
    public Level selectLevelByOverpaperId(Integer createId) {
        return userMapper.selectLevelByOverpaperId(createId);

    }

    @Override
    public Teacher selectTeacherByCategoryAndTeacherName(String category, String teacherName) {

        return userMapper.selectTeacherByCategoryAndTeacherName(category,teacherName);
    }

    @Override
    public void addFavorite(Integer userId, Integer overpaperId) {
        userMapper.addFavorite(userId,overpaperId);

    }

    @Override
    public Enshrine selectEnshrineByOverpaperIdAndUserId(Integer id, Integer paperId) {

        return userMapper.selectEnshrineByOverpaperIdAndUserId(id,paperId);
    }

    @Override
    public void unFavorite(Integer userId, Integer overpaperId) {
        userMapper.unFavorite(userId,overpaperId);
    }

    @Override
    public void updateUserImage(Integer id, String temp) {
        userMapper.updateUserImage(id,temp);
    }

    @Override
    public void editUserVisible(Integer userPhoneisvisible, Integer phoneisvisible, Integer userId) {
        userMapper.editUserVisible(userPhoneisvisible,phoneisvisible,userId);
    }


}
