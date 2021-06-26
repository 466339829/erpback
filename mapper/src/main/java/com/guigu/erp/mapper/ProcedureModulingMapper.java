package com.guigu.erp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.erp.pojo.ProcedureModuling;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProcedureModulingMapper extends BaseMapper<ProcedureModuling> {
    @Select("select pmi.* from `m_procedure_moduling` pmi\n" +
            "left join `m_proceduring` pi on pi.`id` = pmi.`parent_id`\n" +
            "left join `m_manufacture` m on pi.`parent_id` = m.`id`\n" +
            "where pi.`procedure_id` = #{procedureId} and m.`id` = #{parentId} order by pmi.id desc ")
    List<ProcedureModuling> selectByPid(@Param("procedureId") String procedureId ,@Param("parentId") int parentId);
}
