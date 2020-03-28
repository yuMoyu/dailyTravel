package com.lh.daily.service;

import com.lh.daily.NotFoundException;
import com.lh.daily.dao.DailyRepository;
import com.lh.daily.po.Daily;
import com.lh.daily.po.Type;
import com.lh.daily.util.MarkdownUtils;
import com.lh.daily.util.MyBeanUtils;
import com.lh.daily.vo.DailyQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DailyServiceImpl implements DailyService{

    @Autowired
    private DailyRepository dailyRepository;

    @Override
    public Daily getDaily(Long id) {
        return dailyRepository.findById(id).orElse(null);
    }

    @Override
    public Daily getAndConvert(Long id) {
        Daily daily = dailyRepository.findById(id).orElse(null);
        if (daily == null) {
            throw new NotFoundException("游记不存在");
        }
        Daily d = new Daily();
        BeanUtils.copyProperties(daily, d);
        String content = d.getContent();
        d.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return d;
    }

    //条件组合+分页查询
    public Page<Daily> listDaily(Pageable pageable, DailyQuery daily) {
        return dailyRepository.findAll(new Specification<Daily>() {
            //对象   查询条件 某一条件表达式
            public Predicate toPredicate(Root<Daily> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //存放查询条件
                List<Predicate> predicates = new ArrayList<>();
                if(!"".equals(daily.getTitle()) && daily.getTitle()!=null) { //非空判断
                    //模糊查询：属性名 属性值
                    predicates.add(cb.like(root.<String>get("title"),"%"+daily.getTitle()+"%"));
                }
                if (daily.getTypeId() != null) {
                    //精准查询：拿到一个type类型的对象，在get（拿到）其id， 与  前台传递过来的daily的type对象的id作比较
                    predicates.add(cb.equal(root.<Type>get("type").get("id"),daily.getTypeId()));
                }
                if (daily.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),daily.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Daily> listDaily(Pageable pageable) {
        return dailyRepository.findAll(pageable);
    }

    @Override
    public Page<Daily> listDaily(String query, Pageable pageable) {
        return dailyRepository.findByQuery(query, pageable);
    }

    @Override
    public List<Daily> listRecommendDailyTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return dailyRepository.findTop(pageable);
    }

    @Transactional
    public Daily saveDaily(Daily daily) {
        if(daily.getId() == null) { //新增
            daily.setCreateTime(new Date());
            daily.setUpdateTime(new Date());
            daily.setViews(0);
        } else { //修改
            daily.setUpdateTime(new Date());
        }
        return dailyRepository.save(daily);
    }

    @Transactional
    public Daily updateDaily(Long id, Daily daily) {
        Daily d = dailyRepository.findById(id).orElse(null);
        //更新的对象不存在
        if (d == null) {
            throw new NotFoundException("该游记不存在");
        }
        //把daily的值赋值给d  MyBeanUtils获取daily属性值为空的属性
        BeanUtils.copyProperties(daily,d, MyBeanUtils.getNullPropertyNames(daily));
        return dailyRepository.save(d);
    }

    @Transactional
    public void deleteDaily(Long id) {
        dailyRepository.deleteById(id);
    }
}
