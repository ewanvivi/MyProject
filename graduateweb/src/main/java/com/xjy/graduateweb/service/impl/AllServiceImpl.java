package com.xjy.graduateweb.service.impl;

import com.xjy.graduateweb.bean.*;
import com.xjy.graduateweb.mapper.AllMapper;
import com.xjy.graduateweb.service.AllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllServiceImpl implements AllService {

    @Autowired
    AllMapper allMapper;

    @Override
    public List<OverPaper> selectByCategory(String category) {
        return allMapper.selectByCategory(category);
    }



    @Override
    public OverPaper selectOverpaperbyId(Integer id) {
        OverPaper overPaper = allMapper.selectOverpaperbyId(id);
        return overPaper;
    }

    @Override
    public See selectIsSee(Integer paperId, Integer userId) {
        See isSee = allMapper.selectIsSee(paperId,userId);
        return isSee;
    }

    @Override
    public void insertUserSee(Integer paperId, Integer userId) {
        allMapper.insertUserSee(paperId,userId);
    }

    @Override
    public Integer selectAllPaperSumSee(Integer paperId) {
        Integer temp = allMapper.selectAllPaperSumSee(paperId);
        return temp;
    }

    @Override
    public User selectUserByPaperId(Integer paperId, Integer createId) {
        return allMapper.selectUserByPaperId(paperId,createId);
    }

    @Override
    public IsVisible selectIsVisibleByUserId(Integer createId, Integer userid) {
        return allMapper.selectIsVisibleByUserId(createId,userid);
    }

    @Override
    public List<OverPaper> selectAll() {
        return  allMapper.selectAll();
    }

    @Override
    public List<Audit> selectNoAudit() {
        return allMapper.selectNoAudit();
    }

    @Override
    public User selectUserByAudit(Integer id) {
        return  allMapper.selectUserByAudit(id);
    }

    @Override
    public User selectUserByAuditIdAndUserId(Integer auditId, Integer userid) {
       return allMapper.selectUserByAuditIdAndUserId(auditId,userid);
    }

    @Override
    public IsVisible NewselectIsVisibleByUserId(Integer userid) {
        return allMapper.NewselectIsVisibleByUserId(userid);
    }


    @Override
    public Audit selectAuditById(Integer auditId) {
        return allMapper.selectAuditById(auditId);
    }

    @Override
    public void insertOverpaperById(OverPaper overPaper) {
        allMapper.insertOverpaperById(overPaper);
    }

    @Override
    public void deleteAuditById(Integer accessId) {
        allMapper.deleteAuditById(accessId);
    }

    @Override
    public List<Menu> selectParentMenu() {
        return allMapper.selectParentMenu();
    }

    @Override
    public List<Menu> selectSecondMenu(Integer parentId) {
        return allMapper.selectSecondMenu(parentId);
    }

    @Override
    public void insertOverpaperToAudit(OverPaper overPaper) {
        allMapper.insertOverpaperToAudit(overPaper);
    }

    @Override
    public Teacher selectTeacherNameByOverpaperTeacherId(Integer teacherId) {
        return allMapper.selectTeacherNameByOverpaperTeacherId(teacherId);
    }

    @Override
    public void updateStatuByAuditId(Integer statu, Integer auditId) {
        allMapper.updateStatuByAuditId(statu,auditId);
    }

    @Override
    public Teacher selectTeacherAuditByUserId(Integer id) {
        return  allMapper.selectTeacherAuditByUserId(id);

    }

    @Override
    public List<Audit> selectAuditByTeacherIdAndName(Integer teacherId, String teacherName, int i) {

        return allMapper.selectAuditByTeacherIdAndName(teacherId,teacherName,i);
    }

}
