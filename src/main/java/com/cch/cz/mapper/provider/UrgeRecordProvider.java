package com.cch.cz.mapper.provider;

import com.cch.cz.base.dao.BuildSql;
import com.cch.cz.base.dao.provider.BaseProvider;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.UrgeRecord;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UrgeRecordProvider extends BaseProvider<UrgeRecord,Long> {
    org.slf4j.Logger logger =  LoggerFactory.getLogger(this.getClass());
    public String findByCase(Map<String, Object> para){
        String sql =new SQL(){{
            SELECT(BuildSql.select(UrgeRecord.class));
            FROM(BuildSql.tablename(UrgeRecord.class));
            WHERE(" case_id =#{case}");
        }}.toString();
        logger.info(sql);
        return sql;
    }
}
