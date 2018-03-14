package com.cch.cz.base.service.impl;

import com.cch.cz.base.dao.BaseMapper;
import com.cch.cz.base.entity.BaseEntity;
import com.cch.cz.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by Administrator on 2017/12/8.
 */
public class BaseServiceImpl<M extends BaseEntity,PK> implements BaseService<M,PK> {
    @Autowired
    BaseMapper<M ,PK> baseMapper;
    public PK save(M m) {
        return baseMapper.save(m);
    }

    public void delete(PK pk) {
        baseMapper.delete(getNewInstance(),pk);
    }

    public List<M> findAll() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<M> clazz = (Class<M>) pt.getActualTypeArguments()[0];
        return baseMapper.findAll(getNewInstance());
    }

    public List<M> findOne(PK pk)  {

        return baseMapper.findOne(getNewInstance(),pk);
    }

    public Long count(M m) {
        return baseMapper.count(m);
    }

    M getNewInstance(){
        M m = null;
        try {
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class<M> clazz = (Class<M>) pt.getActualTypeArguments()[0];
            m = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return m;
    }

}
