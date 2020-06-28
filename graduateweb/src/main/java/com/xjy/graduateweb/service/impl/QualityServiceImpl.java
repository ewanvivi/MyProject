package com.xjy.graduateweb.service.impl;

import com.xjy.graduateweb.bean.Menu;
import com.xjy.graduateweb.bean.Teacher;
import com.xjy.graduateweb.mapper.QualityMapper;
import com.xjy.graduateweb.service.QualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QualityServiceImpl implements QualityService {

    @Autowired
    QualityMapper qualityMapper;

    @Override
    public List<Menu> getAllSecondaryMenu(String pid) {
        List<Menu> list = qualityMapper.selectSecondaryMenu(pid);
        return list;
    }

    @Override
    public List<Teacher> selectTeacherByCategory(String category) {
        return qualityMapper.selectTeacherByCategory(category);

    }
}
