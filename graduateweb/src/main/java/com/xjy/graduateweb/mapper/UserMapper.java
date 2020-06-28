package com.xjy.graduateweb.mapper;

import com.xjy.graduateweb.bean.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    User selectByLoginaccountAndPassword(@Param("loginaccount") String loginaccount,@Param("password") String password);

    String isRepetition(@Param("loginaccount") String loginaccount);

    void insertUser(User user);

    void insertUserCookie(@Param("token") String token, @Param("id") Integer id);

    User getUserCookie(@Param("cookieValue") String cookieValue);

    User selectUserById(@Param("id") Integer id);

    void editUserInfo(User user);

    Integer selectUserByLoginaccount(@Param("loginaccount") String loginaccount);

    List<OverPaper> selectEnshrineById(@Param("userId") Integer id);

    Integer selectSumSeeByOverpaperId(@Param("overpaperId") Integer id);

    Integer selectLike(@Param("overpaperId")Integer id);

    List<OverPaper> selectAccessGuraduateByUserId(@Param("userId") Integer id);

    Level selectLevelByOverpaperId(@Param("userId") Integer createId);

    Teacher selectTeacherByCategoryAndTeacherName(@Param("category") String category,@Param("teacherName") String teacherName);

    void addFavorite(@Param("userId") Integer userId, @Param("overpaperId") Integer overpaperId);

    Enshrine selectEnshrineByOverpaperIdAndUserId(@Param("userId") Integer id,@Param("paperId") Integer paperId);

    void unFavorite(@Param("userId")Integer userId,@Param("overpaperId") Integer overpaperId);

    void updateUserImage(@Param("userId") Integer id, @Param("image") String temp);

    void editUserVisible(@Param("qqisvisible") Integer userPhoneisvisible, @Param("phoneisvisible") Integer phoneisvisible,@Param("id") Integer userId);
}
