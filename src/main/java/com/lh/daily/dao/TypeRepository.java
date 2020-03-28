package com.lh.daily.dao;

import com.lh.daily.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * 数据库操作
 */
public interface TypeRepository extends JpaRepository<Type, Long> {

    Type findByName(String name);

    @Query("select t from t_type t") //自定义查询，根据分页获取第一页数据
    List<Type> findTop(Pageable pageable);
}
