package com.routon.pmax.common.dao;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Title: GenericDao
 * </p>
 * <p>
 * Description: 定义泛型Dao接口，用于完成基本的CRUD操作
 * 
 * @author zzh
 * @version 1.0
 */
public interface GenericDao<T, PK extends Serializable> {
    public T findById(PK id);

    public List<T> findAll();

    public T save(T entity);

    public void update(T entity);

    public void delete(T entity);
}
