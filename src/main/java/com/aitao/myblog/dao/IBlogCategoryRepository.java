package com.aitao.myblog.dao;

import com.aitao.myblog.domain.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/11/2
 * Time : 1:36
 * Information : 博客分类持久层接口
 */
public interface IBlogCategoryRepository extends JpaRepository<BlogCategory, Long>, JpaSpecificationExecutor<BlogCategory> {
    /**
     * 通过博客分类名查找指定的分类信息
     *
     * @param name 博客分类名
     * @return 返回博客分类对象
     */
    @Query("select bc from BlogCategory as bc where bc.name=:name")
    BlogCategory getCategoryByName(@Param("name") String name);

    /**
     * 根据指定分类编号删除分类信息
     *
     * @param id 分类编号
     * @return 返回记录数
     */
    @Transactional
    @Modifying
    @Query("delete from BlogCategory as bc where bc.id=:id")
    int deleteCategoryById(@Param("id") Long id);

    /**
     * 根据指定分类名删除分类信息
     *
     * @param name 分类名
     * @return 返回记录数
     */
    @Transactional
    @Modifying
    @Query("delete from BlogCategory as bc where bc.name=:name")
    int deleteCategoryByName(@Param("name") String name);

    /**
     * 更新分类信息
     *
     * @param category
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "update BlogCategory as bc set bc.status=:#{#category.status},bc.updateTime=:#{#category.updateTime} where bc.id=:#{#category.id}")
    int updateCategory(@Param("category") BlogCategory category);

    /**
     * 更新分类状态
     *
     * @param status
     * @return
     */
    @Transactional
    @Modifying
    @Query("update BlogCategory as bc set bc.status=:status where bc.id=:id")
    int updateCategoryStatus(Long id, Byte status);

    /**
     * 获取所有启用状态的分类
     *
     * @return 返回BlogCategory集合对象
     */
    @Query("select bc from BlogCategory as bc where bc.status=:status")
    List<BlogCategory> getCategoryByStatus(@Param("status") Byte status);
}
