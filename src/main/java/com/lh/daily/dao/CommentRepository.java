package com.lh.daily.dao;

import com.lh.daily.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    //根据dailuid查询评论中parentComment为空的（顶级评论）
    List<Comment> findByDailyIdAndParentCommentNull(Long id, Sort sort);
}
