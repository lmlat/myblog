package com.aitao.myblog.controller;

import com.aitao.myblog.domain.Comment;
import com.aitao.myblog.domain.User;
import com.aitao.myblog.service.IBlogService;
import com.aitao.myblog.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @Author : AiTao
 * @Create : 2020/12/22 15:39
 * @Description : 博客评论控制器
 */
@Controller
public class BlogCommentController {
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IBlogService blogService;
    @Value("${comment.avatar}")
    private String avatar;

    @RequestMapping({"/comments","/comments/"})
    public String comment(Model model) {
        model.addAttribute("comments", commentService.listCommentsLimit());
        return "blog-comments";
    }

    /**
     * 加载指定的fragments片段
     *
     * @param id 博客编号
     */
    @GetMapping("/comments/{id}")
    public String loadCommentListFragments(@PathVariable("id") Long id,
                                           Model model) {
        model.addAttribute("comments", commentService.listCommentsById(id));
        return "blog-detailed :: commentList";
    }

    /**
     * 添加评论
     *
     * @param comment 评论信息
     */
    @PostMapping("/comments")
    public String saveComment(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlogById(blogId));
        User user = (User) session.getAttribute("USER");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminStatus((byte) 1);
        } else {
            comment.setAvatar(avatar);
            comment.setAdminStatus((byte) 0);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }

}
