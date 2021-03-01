package com.aitao.myblog.controller;

import com.aitao.myblog.domain.Banner;
import com.aitao.myblog.domain.Blog;
import com.aitao.myblog.service.IBlogService;
import com.aitao.myblog.service.ICommentService;
import com.aitao.myblog.utils.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Author : AiTao
 * Date : 2020/10/18
 * Time : 17:27
 * Information : 博客首页控制器
 */
@Controller
@RequestMapping("/index")
public class BlogIndexController {
    @Autowired
    private IBlogService blogService;

    @Autowired
    private ICommentService commentService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping({"", "/"})
    public String index(/*@PathVariable("id") Integer id,@PathVariable("name") String name*/
            @PageableDefault(size = 12, sort = {"views"}, direction = Sort.Direction.DESC) Pageable latestPageable,
            Model model,
            HttpSession session) {
        Page<Blog> blogs = blogService.listLatestBlog(latestPageable);
        List<Blog> recommendBlogs = blogService.listRecommendBlog();
        List<Banner> banners = blogService.listBannerBlog();
        //文章总数
        Long countBlog = blogService.countBlogs();
        //总访问量
        Long countVisit = blogService.countVisits();
        //评论总数
        Long countComment = commentService.countComments();
        //留言总数
        Long countReply = commentService.countReplys();
        System.out.println(countBlog);
        System.out.println(countVisit);
        System.out.println(countComment);
        System.out.println(countReply);
        model.addAttribute("blogs", blogs);
        model.addAttribute("recommendBlogs", recommendBlogs);
        model.addAttribute("banners", banners);
        session.setAttribute("countBlog", countBlog);
        session.setAttribute("countVisit", countVisit);
        session.setAttribute("countComment", countComment);
        session.setAttribute("countReply", countReply);
        return "index";
    }

    @GetMapping("/page/{number}")
    public String pageSearch(@PathVariable("number") Integer number,
                             Model model) {
        //容错
        if (number < 0) {
            number = 0;
        }
        //定义排序规则(数据库中从0开始是第一页)
        Pageable currPageable = PageRequest.of(number, 12, Sort.by(Sort.Direction.DESC, "views"));
        Page<Blog> blogs = blogService.listLatestBlog(currPageable);
        System.out.println("page:" + currPageable.getPageNumber());
        System.out.println("pageSize:" + currPageable.getPageSize());
        System.out.println("offset:" + currPageable.getOffset());
        System.out.println("total:" + blogs.getContent().size());
        model.addAttribute("CURRENT_BLOG", blogs);
        return "blog-next";
    }

    @GetMapping("/search/{keyword}")
    public String search(@PathVariable("keyword") String keyword,
                         Model model) {
        List<Blog> blogs = blogService.listKeywordBlog(keyword);
        model.addAttribute("blogs",blogs);
        model.addAttribute("KEYWORD",keyword);
        return "blog-search";
    }
}
