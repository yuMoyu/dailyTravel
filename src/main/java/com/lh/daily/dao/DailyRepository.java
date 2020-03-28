package com.lh.daily.dao;

import com.lh.daily.po.Daily;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DailyRepository extends JpaRepository<Daily, Long>, JpaSpecificationExecutor<Daily> {
    //JpaSpecificationExecutor 实现组合查询

    @Query("select d from t_daily d where d.recommend = true")
    List<Daily> findTop(Pageable pageable);

    @Query("select d from t_daily d where d.title like ?1 or d.content like ?1 or d.description like ?1") //查询题目或内容或描述中有关键字的
    Page<Daily> findByQuery(String query, Pageable pageable);
}
