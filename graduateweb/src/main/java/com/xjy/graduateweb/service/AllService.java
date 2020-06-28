package com.xjy.graduateweb.service;

import com.xjy.graduateweb.bean.*;

import java.util.List;

public interface AllService {

    List<OverPaper> selectByCategory(String category);


    OverPaper selectOverpaperbyId(Integer id);

    See selectIsSee(Integer paperId, Integer userId);

    void insertUserSee(Integer paperId, Integer userId);

    Integer selectAllPaperSumSee(Integer paperId);

    User selectUserByPaperId(Integer paperId, Integer createId);

    IsVisible selectIsVisibleByUserId(Integer createId, Integer userid);

    List<OverPaper> selectAll();

    List<Audit> selectNoAudit();

    User selectUserByAudit(Integer id);

    User selectUserByAuditIdAndUserId(Integer auditId, Integer userid);

    IsVisible NewselectIsVisibleByUserId(Integer userid);

    Audit selectAuditById(Integer auditId);

    void insertOverpaperById(OverPaper overPaper);

    void deleteAuditById(Integer accessId);

    List<Menu> selectParentMenu();

    List<Menu> selectSecondMenu(Integer parentId);

    void insertOverpaperToAudit(OverPaper overPaper);

    Teacher selectTeacherNameByOverpaperTeacherId(Integer teacherId);

    void updateStatuByAuditId(Integer statu, Integer auditId);

    Teacher selectTeacherAuditByUserId(Integer id);

    List<Audit> selectAuditByTeacherIdAndName(Integer teacherId, String teacherName, int i);
}
