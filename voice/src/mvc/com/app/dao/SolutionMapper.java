package com.app.dao;

import com.code.model.Solution;
import com.code.model.SolutionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SolutionMapper {
    int countByExample(SolutionExample example);

    int deleteByExample(SolutionExample example);

    int deleteByPrimaryKey(Integer solutionId);

    int insert(Solution record);

    int insertSelective(Solution record);

    List<Solution> selectByExampleWithBLOBs(SolutionExample example);

    List<Solution> selectByExample(SolutionExample example);

    Solution selectByPrimaryKey(Integer solutionId);

    int updateByExampleSelective(@Param("record") Solution record, @Param("example") SolutionExample example);

    int updateByExampleWithBLOBs(@Param("record") Solution record, @Param("example") SolutionExample example);

    int updateByExample(@Param("record") Solution record, @Param("example") SolutionExample example);

    int updateByPrimaryKeySelective(Solution record);

    int updateByPrimaryKeyWithBLOBs(Solution record);

    int updateByPrimaryKey(Solution record);
    
    List<Solution> GetHistoryMistake(String username);
}