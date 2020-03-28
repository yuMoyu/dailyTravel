package com.lh.daily.service;

import com.lh.daily.NotFoundException;
import com.lh.daily.dao.TagRepository;
import com.lh.daily.handler.StringHandler;
import com.lh.daily.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService{

    @Autowired
    private TagRepository tagRepository;

    @Transactional //事务，保持数据的一致性
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    public Tag getTag(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        //按照dailys列表的大小降序排序，取第一页
        Sort sort = Sort.by(Sort.Direction.DESC,"dailys.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return tagRepository.findTop(pageable);
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(convertToList(ids));
    }
    //将id放入list
    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        List<Tag> tags = tagRepository.findAll();
        if (!"".equals(ids) && ids!=null) {
            String[] idarr = ids.split(",");
            for (int i=0; i<idarr.length; i++) {
                //新增数据有三种情况，一种是字符串，一种是数字,一种是数字和id相同（暂且不允许是数字）
                if(StringHandler.stringtoLongException(idarr[i])) { //判断是否为数字
                    //将数据封装为tag对象,调用save放入数据库
                    Tag newTag = new Tag();
                    newTag.setName(idarr[i]);
                    saveTag(newTag);
                    idarr[i] = tagRepository.findByName(idarr[i]).getId().toString();
                }
                list.add(new Long(idarr[i]));
            }
        }
        return list;
    }

    @Transactional
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.findById(id).orElse(null);
        if (t == null) {
            throw new NotFoundException("不存在该类型");
        }
        //把tag的值赋给t对象
        BeanUtils.copyProperties(tag,t);
        return tagRepository.save(t);
    }

    @Transactional
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
