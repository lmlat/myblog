package com.aitao.myblog.controller;

import com.aitao.myblog.domain.Blog;
import com.aitao.myblog.service.IBlogService;
import com.aitao.myblog.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author : AiTao
 * Date : 2020/10/23
 * Time : 23:45
 * Information : 博客历史浏览时间线控制器
 */
@Controller
@RequestMapping("/time")
public class BlogTimeController {
    @Autowired
    private IBlogService blogService;

    @RequestMapping({"", "/"})
    public String time(@PageableDefault(size = 7, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       Model model) {
        Page<Blog> historyBlogs = blogService.listHistoryBlog(pageable);
        model.addAttribute("historyBlogs", historyBlogs);
        return "blog-time";
    }

    @GetMapping("/page/{number}")
    public String historyBlogLimit(@PathVariable("number") Integer number,
                                   Model model) {
        System.out.println("page:" + number);
        Pageable currPageable = PageRequest.of(number, 7, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<Blog> historyBlogs = blogService.listHistoryBlog(currPageable);
        System.out.println(historyBlogs.getContent());
        model.addAttribute("historyBlogs", historyBlogs);
        return "blog-time";
    }
}
