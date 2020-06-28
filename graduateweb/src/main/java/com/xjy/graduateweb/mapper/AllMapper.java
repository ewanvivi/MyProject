package com.xjy.graduateweb.mapper;

import com.xjy.graduateweb.bean.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AllMapper {

    List<OverPaper> selectByCategory(@Param("category")String category);


    OverPaper selectOverpaperbyId(@Param("id") Integer id);

    See selectIsSee(@Param("overpaperId") Integer paperId,@Param("userId") Integer userId);

    void insertUserSee(@Param("overpaperId") Integer paperId,@Param("userId") Integer userId);

    Integer selectAllPaperSumSee(@Param("overpaperId") Integer paperId);

    User selectUserByPaperId(@Param("paperId") Integer paperId, @Param("createId") Integer createId);

    IsVisible selectIsVisibleByUserId(@Param("createId")Integer createId, @Param("userId")Integer userid);

    List<OverPaper> selectAll();

    List<Audit> selectNoAudit();

    User selectUserByAudit(@Param("id") Integer id);

    User selectUserByAuditIdAndUserId(@Param("auditId") Integer auditId,@Param("userid") Integer userid);

    IsVisible NewselectIsVisibleByUserId(@Param("id") Integer userid);

    Audit selectAuditById(@Param("id") Integer auditId);

    void insertOverpaperById(OverPaper overPaper);

    void deleteAuditById(@Param("id") Integer accessId);

    List<Menu> selectParentMenu();

    List<Menu> selectSecondMenu(@Param("parentId") Integer parentId);

    void insertOverpaperToAudit(OverPaper overPaper);

    Teacher selectTeacherNameByOverpaperTeacherId(@Param("teacherId") Integer teacherId);

    void updateStatuByAuditId(@Param("statu")Integer statu,@Param("id") Integer id);

    Teacher selectTeacherAuditByUserId(@Param("userId") Integer id);

    List<Audit> selectAuditByTeacherIdAndName(@Param("teacherId") Integer teacherId,@Param("teacherName") String teacherName,@Param("num") int i);
}
