package com.lh.daily.dao;

import com.lh.daily.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 数据库操作
 */
public interface TypeRepository extends JpaRepository<Type, Long> {

    Type findByName(String name);
}
