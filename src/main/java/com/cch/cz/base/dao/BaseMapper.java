package com.cch.cz.base.dao;


import com.cch.cz.base.dao.provider.BaseProvider;
import com.cch.cz.base.entity.BaseEntity;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */
public interface BaseMapper<M extends BaseEntity,PK> {
    @InsertProvider(type = BaseProvider.class,method = "save")
    void save(M m);
    @DeleteProvider(type = BaseProvider.class,method = "delete")
    void delete(M m, PK pk);
    @SelectProvider(type = BaseProvider.class,method = "findAll")
    List<M> findAll(M m);
    @SelectProvider(type = BaseProvider.class,method = "findOne")
    M findOne(M m, PK pk);
    @SelectProvider(type = BaseProvider.class,method = "count")
    Long count(M m);


}
