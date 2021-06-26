package com.guigu.erp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.erp.pojo.Procedure;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface ProcedureMapper extends BaseMapper<Procedure> {

    @Update("update m_procedure set procedure_finish_tag = #{procedureFinishTag} where id =#{id} ")
    boolean updateFinishTag(@Param("id") int id,@Param("procedureFinishTag") String procedureFinishTag);
}
