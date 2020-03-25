package com.lh.daily.service;

import com.lh.daily.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TypeService {
    /**
     * 新增type
     * @param type
     * @return
     */
    Type saveType(Type type);

    /**
     * 查询type
     * @param id
     * @return
     */
    Type getType(Long id);

    /**
     * 根据分类名查找分类
     * @param name
     * @return
     */
    Type getTypeByName(String name);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<Type> listType(Pageable pageable);

    /**
     * 根据id修改type
     * @param id
     * @param type
     * @return
     */
    Type updateType(Long id, Type type);

    /**
     * 根据id删除type
     * @param id
     */
    void deleteType(Long id);
}
