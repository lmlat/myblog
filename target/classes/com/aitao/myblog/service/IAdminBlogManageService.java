package com.aitao.myblog.service;

import com.aitao.myblog.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/11/7
 * Time : 15:37
 * Information : 博客管理业务层接口
 */
public interface IAdminBlogManageService {
    /**
     * 博客分页显示(5条/页)
     *
     * @return 返回分页的数据
     */
    Page<Blog> listBlogLimit(Blog blog, Pageable pageable);

    /**
     * 获取博客最新且按标题降序排序后的博客数据
     *
     * @return 返回Blog集合
     */
    List<Blog> listBlogLimit8();

    /**
     * 根据博客编号获取对应博客信息
     *
     * @param id 编号编号
     * @return 博客
     */
    Blog getBlogById(Long id);

    /**
     * 统计博客总数
     *
     * @return 返回博客文章总数
     */
    long countBlog();

    /**
     * 新建博客
     *
     * @param blog 博客信息
     * @return 返回博客对象
     */
    Blog saveBlog(Blog blog);

    /**
     * 删除指定id的博客文章
     *
     * @param id 文章编号
     */
    void deleteById(Long id);

    /**
     * 更新博客信息
     *
     * @param blog 博客信息
     * @return 返回记录数
     */
    Blog updateBlog(Blog blog);

    /**
     * 博客文章加入回收站
     *
     * @param id 文章编号
     * @return 返回记录数
     */
    int updateBlogStatusById(Long id);
}
