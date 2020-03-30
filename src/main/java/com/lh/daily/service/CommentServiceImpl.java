package com.lh.daily.service;

import com.lh.daily.dao.CommentRepository;
import com.lh.daily.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByDailyId(Long dailyId) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createTime");
        //获取到每个结点顶级的数据
        List<Comment> comments = commentRepository.findByDailyIdAndParentCommentNull(dailyId, sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        //获取父评论id
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {//若有父级评论
            //将父级评论放入comment
            comment.setParentComment(commentRepository.findById(parentCommentId).orElse(null));
        } else {//没有父级评论
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    /**
     * 循环每个顶点的评论结点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for(Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            //添加每个顶点的评论结点
            commentsView.add(c);
        }
        //合并评论的各层子代均列到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     * @param comments
     */
    private void combineChildren(List<Comment> comments) {
        for(Comment comment : comments) {
            //找到父评论的子集合
            List<Comment> replys1 = comment.getReplyComments();
            for (Comment reply1 : replys1) {
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }
    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    private void recursively(Comment comment) {
        tempReplys.add(comment);
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }
}
