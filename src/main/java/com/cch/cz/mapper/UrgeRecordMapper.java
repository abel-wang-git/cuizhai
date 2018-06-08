package com.cch.cz.mapper;

import com.cch.cz.base.dao.BaseMapper;
import com.cch.cz.entity.UrgeRecord;
import com.cch.cz.mapper.provider.CasesProvider;
import com.cch.cz.mapper.provider.UrgeRecordProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 委案
 */
@Mapper
public interface UrgeRecordMapper extends BaseMapper<UrgeRecord,Long> {
    @SelectProvider(type = UrgeRecordProvider.class,method = "findByCase")
    List<UrgeRecord> findByCase(@Param("case") Long id);

    @SelectProvider(type = UrgeRecordProvider.class,method = "manager")
    List<Map> manager(Map c);

    @Update("update t_urge_record set staff_id = #{staffId} where case_id=#{caseId}")
    void updateByCase(@Param("staffId") String staff, @Param("caseId") Long id);
}
