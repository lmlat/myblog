package com.aitao.myblog.service;

import com.aitao.myblog.domain.BlogCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/11/2
 * Time : 1:38
 * Information :  博客分类业务层接口
 */
public interface IBlogCategoryService {
    /**
     * 增加一条博客分类信息
     *
     * @param category 博客分类集合(博客分类名称不区分大小写)
     * @return 返回记录数
     */
    BlogCategory saveCategory(BlogCategory category);

    /**
     * 根据指定编号获取博客分类信息
     *
     * @param id 博客分类编号
     * @return
     */
    BlogCategory getCategoryById(Long id);

    /**
     * 通过博客分类名查找指定的分类信息
     *
     * @param name 博客分类名
     * @return 返回博客分类对象
     */
    BlogCategory getCategoryByName(String name);

    /**
     * 根据分类编号删除指定的博客分类信息
     *
     * @param id 分类编号
     * @return
     */
    void deleteCategory(Long id);

    /**
     * 获取所有博客分类信息
     *
     * @return 返回博客分类集合
     */
    Page<BlogCategory> listCategoryLimit(Pageable pageable);

    /**
     * 修改指定博客分类信息
     *
     * @param category 博客分类信息
     * @return 返回记录数
     */
    BlogCategory updateCategory(Long id, BlogCategory category);

    /**
     * 获取所有启用状态的分类
     *
     * @return 返回BlogCategory集合对象
     */
    List<BlogCategory> getCategoryByStatus(Byte getCategoryByStatus);
}
