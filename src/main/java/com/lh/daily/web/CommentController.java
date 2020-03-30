package com.lh.daily.web;

import com.lh.daily.po.Comment;
import com.lh.daily.po.User;
import com.lh.daily.service.CommentService;
import com.lh.daily.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private DailyService dailyService;
    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{dailyId}")
    public String commentList(@PathVariable Long dailyId, Model model) {//获取评论列表
        model.addAttribute("comments",commentService.listCommentByDailyId(dailyId));
        return "daily :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long dailyId = comment.getDaily().getId();
        comment.setDaily(dailyService.getDaily(dailyId));
        User user = (User)session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
//            comment.setNickname(user.getNickname());
        } else {
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/"+dailyId;
    }
}
