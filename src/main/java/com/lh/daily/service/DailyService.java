package com.lh.daily.service;

import com.lh.daily.po.Daily;
import com.lh.daily.vo.DailyQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DailyService {

    /**
     * 根据id查询游记
     * @param id
     * @return
     */
    Daily getDaily(Long id);

    /**
     * markdown转换为html
     * @param id
     * @return
     */
    Daily getAndConvert(Long id);

    /**
     * 分页查询+条件查询 查询条件封装为一个对象
     */
    Page<Daily> listDaily(Pageable pageable, DailyQuery dailyQuery);

    Page<Daily> listDaily(Pageable pageable);

    /**
     * 全局搜索游记
     */
    Page<Daily> listDaily(String query, Pageable pageable);
    /**
     * 获取最新推荐游记
     * @return
     */
    List<Daily> listRecommendDailyTop(Integer size);
    /**
     * 新增daily
     * @param daily
     * @return
     */
    Daily saveDaily(Daily daily);

    /**
     * 根据id查询需要修改的daily，然后再把新的daily赋值给它
     * @param id
     * @param daily
     * @return
     */
    Daily updateDaily(Long id, Daily daily);

    void deleteDaily(Long id);
}
