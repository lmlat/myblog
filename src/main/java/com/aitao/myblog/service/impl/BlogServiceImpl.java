package com.aitao.myblog.service.impl;

import com.aitao.myblog.dao.IBannerRepository;
import com.aitao.myblog.dao.IBlogRepository;
import com.aitao.myblog.domain.Banner;
import com.aitao.myblog.domain.Blog;
import com.aitao.myblog.service.IBannerService;
import com.aitao.myblog.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/10/27
 * Time : 15:50
 * Information : 博客详情业务层接口实现类
 */
@Service
public class BlogServiceImpl implements IBlogService {

    @Autowired
    private IBlogRepository blogRepository;
    @Autowired
    private IBannerRepository bannerRepository;

    /**
     * 最热文章(10篇)
     *
     * @return 返回文章集合
     */
    @Override
    public Page<Blog> listLatestBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    /**
     * 推荐文章(6篇)
     *
     * @return 返回文章集合
     */
    @Override
    public List<Blog> listRecommendBlog() {
        return blogRepository.listRecommendBlog();
    }

    /**
     * 轮播图文章(8篇) 根据title排序
     *
     * @return 返回Banner集合
     */
    @Override
    public List<Banner> listBannerBlog() {
        return bannerRepository.listBannerBlog();
    }

    /**
     * 历史博客(7篇)
     *
     * @return 返回文章集合
     */
    @Override
    public Page<Blog> listHistoryBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    /**
     * 根据指定id获取博客信息
     *
     * @param id 编号
     * @return 返回Blog
     */
    @Override
    public Blog getBlogById(Long id) {
        return blogRepository.getOne(id);
    }

    /**
     * 更新文章浏览数
     *
     * @param id 文章编号
     * @return
     */
    @Override
    public int updateBlogViews(Long id) {
        return blogRepository.updateBlogViews(id);
    }

    /**
     * 模糊搜索文章
     *
     * @param keyword 关键字
     * @return
     */
    @Override
    public List<Blog> listKeywordBlog(String keyword) {
        keyword = "%" + keyword + "%";
        return blogRepository.listKeywordBlog(keyword);
    }

    /**
     * 统计总访问量
     */
    @Override
    public Long countVisits() {
        return blogRepository.countVisits();
    }

    /**
     * 统计文章数
     *
     * @return
     */
    @Override
    public Long countBlogs() {
        return blogRepository.count();
    }
}
