package com.xjy.graduateweb.service;

import com.xjy.graduateweb.bean.*;

import java.util.List;

public interface UserService {
    User selectByLoginaccountAndPassword(String loginaccount, String password);

    String isRepetition(String loginaccount);

    void insertUser(User user);

    void insertUserCookie(String token, Integer id);

    User getUserCookie(String cookieValue);

    User selectUserById(Integer id);

    void editUserInfo(User user);

    Integer selectUserByLoginaccount(String loginaccount);

    List<OverPaper> selectEnshrineById(Integer id);

    Integer selectSumSeeByOverpaperId(Integer id);

    Integer selectLike(Integer id);

    List<OverPaper> selectAccessGuraduateByUserId(Integer id);

    Level selectLevelByOverpaperId(Integer createId);

    Teacher selectTeacherByCategoryAndTeacherName(String category, String teacherName);

    void addFavorite(Integer userId, Integer overpaperId);

    Enshrine selectEnshrineByOverpaperIdAndUserId(Integer id, Integer paperId);

    void unFavorite(Integer userId, Integer overpaperId);

    void updateUserImage(Integer id, String temp);

    void editUserVisible(Integer userPhoneisvisible, Integer phoneisvisible, Integer userId);
}
