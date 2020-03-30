package com.lh.daily.dao;

import com.lh.daily.po.Daily;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DailyRepository extends JpaRepository<Daily, Long>, JpaSpecificationExecutor<Daily> {
    //JpaSpecificationExecutor 实现组合查询

    @Query("select d from t_daily d where d.recommend = true")
    List<Daily> findTop(Pageable pageable);

    @Query("select d from t_daily d where d.title like ?1 or d.content like ?1 or d.description like ?1") //查询题目或内容或描述中有关键字的
    Page<Daily> findByQuery(String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update t_daily d set d.views = d.views+1 where d.id = ?1")
    int updateViews(Long id);

    @Query("select function('date_format',d.updateTime,'%Y') as year from t_daily d group by year order by MIN(d.updateTime) desc ") //按照年份
    List<String> findGroupYear(); //返回年份

    @Query("select d from t_daily d where function('date_format',d.updateTime,'%Y') = ?1")
    List<Daily> findByYear(String year); //按年份对游记查询
}
