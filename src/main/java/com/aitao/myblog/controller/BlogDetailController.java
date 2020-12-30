package com.aitao.myblog.controller;

import com.aitao.myblog.domain.Blog;
import com.aitao.myblog.service.IBlogService;
import com.aitao.myblog.service.ICommentService;
import com.aitao.myblog.utils.MarkDownUtils;
import com.aitao.myblog.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author : AiTao
 * @Create : 2020/12/16 8:51
 * @Description : 博客详情控制器
 */
@Controller
@RequestMapping("/detailed")
public class BlogDetailController {
    @Autowired
    private IBlogService blogService;
    @Autowired
    private ICommentService commentService;

    /**
     * 加载博客详情页
     */
    @RequestMapping({"", "/"})
    public String detail() {
        return "blog-detailed";
    }

    /**
     * 浏览指定的文章
     */
    @GetMapping("/{id}/{title}")
    public String lookArticle(@PathVariable("id") String id,
                              @PathVariable("title") String title,
                              Model model) {
        Long no = StringUtils.strToLong(id);
        blogService.updateBlogViews(no);
        Blog blog = blogService.getBlogById(no);
        //markdown格式转换成HTML格式
        blog.setContent(MarkDownUtils.markDownToHtmlExtension(blog.getContent()));
        blog.setComments(commentService.listCommentsById(blog.getId()));
        System.out.println("blog:" + blog);
        model.addAttribute("CURRENT_BLOG", blog);
        model.addAttribute("HEAD_TITLE", title.concat(" | 嘟嘟小梦"));
        model.addAttribute("comments", blog.getComments());
        return "blog-detailed";
    }
}
