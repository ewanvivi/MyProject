package com.xjy.graduateweb.mapper;

import com.xjy.graduateweb.bean.Menu;
import com.xjy.graduateweb.bean.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QualityMapper {
    List<Menu> selectSecondaryMenu(@Param("pid") String pid);

    List<Teacher> selectTeacherByCategory(@Param("name") String category);
}
