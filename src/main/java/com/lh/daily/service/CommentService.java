package com.lh.daily.service;

import com.lh.daily.po.Comment;
import java.util.List;

public interface CommentService {

    /**
     * 根据游记id查询对应的评论列表
     * @param dailyId
     * @return
     */
    List<Comment> listCommentByDailyId(Long dailyId);
    Comment saveComment(Comment comment);
}
