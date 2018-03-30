package com.youcash.uc.mapper;


import com.youcash.uc.entity.Emp;
import com.youcash.uc.entity.EmpExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmpMapper {
    int countByExample(EmpExample example);

    int deleteByExample(EmpExample example);

    int deleteByPrimaryKey(Short empno);

    int insert(Emp record);

    int insertSelective(Emp record);

    List<Emp> selectByExample(EmpExample example);

    Emp selectByPrimaryKey(Short empno);

    int updateByExampleSelective(@Param("record") Emp record, @Param("example") EmpExample example);

    int updateByExample(@Param("record") Emp record, @Param("example") EmpExample example);

    int updateByPrimaryKeySelective(Emp record);

    int updateByPrimaryKey(Emp record);
}