package com.aitao.myblog.service;

import com.aitao.myblog.domain.BlogCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/11/7
 * Time : 0:18
 * Information : 分类管理业务层接口
 */
public interface IAdminCategoryManageService {
    /**
     * 新建博客分类
     *
     * @param category 博客分类对象
     * @return 返回记录数
     */
    BlogCategory saveCategory(BlogCategory category);

    /**
     * 博客分类列表(降序排序)
     *
     * @return 返回所有博客分类集合
     */
    List<BlogCategory> categoryOrderByIdDescList();

    /**
     * 博客分类列表(升序排序)
     *
     * @return 返回所有分类集合
     */
    Page<BlogCategory> categoryOrderByIdAscList(Pageable pageable);

    /**
     * 删除指定编号的博客分类
     *
     * @param id 分类编号
     * @return 1删除成功 0删除失败
     */
    int deleteCategoryById(Long id);

    /**
     * 删除指定分类名的博客分类
     * @param name 博客分类名
     * @return 1删除成功 0删除失败
     */
    int deleteCategoryByName(String name);

    /**
     * 更新博客分类信息
     * @param category 博客分类对象
     * @return
     */
    BlogCategory updateCategory(BlogCategory category);

    /**
     * 根据分类名获取指定分类信息
     * @param name 分类名
     * @return 返回分类信息对象
     */
    BlogCategory getCategoryByName(String name);

    /**
     * 根据分类编号获取指定分类信息
     * @param id 分类编号
     * @return 返回分类信息对象
     */
    BlogCategory getCategoryById(Long id);

    /**
     * 更新分类状态
     * @param category 分类信息
     * @return 返回记录数
     */
    int updateCategoryStatus(BlogCategory category);
}
