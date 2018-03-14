package com.cch.cz.base.service;

import java.util.List;

/**
 * Created by Administrator on 2017/12/8.
 */
public interface BaseService<M,PK> {
    PK save(M m);

    void delete(PK pk) ;

    List<M> findAll() ;

    List<M> findOne(PK pk) ;

    Long count(M m);
}
