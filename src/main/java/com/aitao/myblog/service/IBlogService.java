package com.aitao.myblog.service;

import com.aitao.myblog.domain.Banner;
import com.aitao.myblog.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/10/27
 * Time : 15:50
 * Information :  博客详情业务层接口
 */
public interface IBlogService {
    /**
     * 最热文章(10篇)
     *
     * @return 返回文章集合
     */
    Page<Blog> listLatestBlog(Pageable pageable);

    /**
     * 推荐文章(6篇)
     *
     * @return 返回文章集合
     */
    List<Blog> listRecommendBlog();

    /**
     * 轮播图文章(8篇)
     *
     * @return 返回文章集合
     */
    List<Banner> listBannerBlog();

    /**
     * 历史博客(7篇)
     *
     * @return 返回文章集合
     */
    Page<Blog> listHistoryBlog(Pageable pageable);

    /**
     * 根据指定id获取博客信息
     *
     * @param id 编号
     * @return 返回Blog
     */
    Blog getBlogById(Long id);

    /**
     * 更新文章浏览数
     *
     * @param id 文章编号
     * @return
     */
    int updateBlogViews(Long id);

    /**
     * 搜索关键字博客
     *
     * @return 返回文章集合
     */
    List<Blog> listKeywordBlog(String keyword);

    /**
     * 统计访问量
     * @return
     */
    Long countVisits();

    /**
     * 统计文章数
     * @return
     */
    Long countBlogs();
}
