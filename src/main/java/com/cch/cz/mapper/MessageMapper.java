package com.cch.cz.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.cch.cz.base.dao.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**

 *

 */
@Mapper
public interface MessageMapper extends BaseMapper<com.cch.cz.entity.Message, java.lang.Long> {

    @Select("select m.id as mid, ms.id,m.message,m.sender,m.date,m.type,m.case_id as caseId,ms.receiver,ms.status ,ms.message_id as messageId  from t_message m left outer join  t_message_status ms on (m.id=ms.message_id)  where (ms.status=#{status}) and (receiver=#{staffid})")
    List<Map> getMessage(@Param("staffid") String staffId, @Param("status") int value);
}