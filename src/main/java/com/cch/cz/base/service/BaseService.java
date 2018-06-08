package com.cch.cz.base.service;

import java.util.List;

/**
 * Created by Administrator on 2017/12/8.
 */
public interface BaseService<M,PK> {
    void save(M m);

    void delete(PK pk) ;

    List<M> findAll() ;

    M findOne(PK pk) ;

    Long count(M m);

    void update(M m);

    List<M> findByEntity(M m);
}
