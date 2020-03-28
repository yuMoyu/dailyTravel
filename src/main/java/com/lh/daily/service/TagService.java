package com.lh.daily.service;

import com.lh.daily.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    /**
     * 新增tag
     * @param tag
     * @return
     */
    Tag saveTag(Tag tag);

    /**
     * 查询tag
     * @param id
     * @return
     */
    Tag getTag(Long id);

    /**
     * 根据标签名查找标签
     * @param name
     * @return
     */
    Tag getTagByName(String name);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();
    List<Tag> listTagTop(Integer size);
    List<Tag> listTag(String ids);
    /**
     * 根据id修改tag
     * @param id
     * @param tag
     * @return
     */
    Tag updateTag(Long id, Tag tag);

    /**
     * 根据id删除tag
     * @param id
     */
    void deleteTag(Long id);
}
