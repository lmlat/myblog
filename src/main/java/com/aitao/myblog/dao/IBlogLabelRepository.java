package com.aitao.myblog.dao;

import com.aitao.myblog.domain.BlogLabel;
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
 * Time : 10:49
 * Information : 博客标签持久层接口
 */
public interface IBlogLabelRepository extends JpaRepository<BlogLabel, Long>, JpaSpecificationExecutor<BlogLabel> {
    /**
     * 通过博客标签名查找指定的博客标签信息
     *
     * @param name 博客标签名
     * @return 返回博客标签对象
     */
    @Query("select bl from BlogLabel as bl where bl.name=:name")
    BlogLabel getLabelByName(@Param("name") String name);


    /**
     * 根据指定id删除标签字段
     *
     * @param id 标签编号
     * @return 返回记录数
     */
    @Transactional
    @Modifying
    @Query("delete from BlogLabel as bl where bl.id=:id")
    int deleteLabelById(@Param("id") Long id);

    /**
     * 根据指定标签名删除标签字段
     *
     * @param name 标签名
     * @return 返回记录数
     */
    @Transactional
    @Modifying
    @Query("delete from BlogLabel as bl where bl.name=:name")
    int deleteLabelByName(@Param("name") String name);

    /**
     * @param label
     * @return
     */
    @Transactional
    @Modifying
    @Query("update BlogLabel as bl set bl.status=:#{#label.status},bl.updateTime=:#{#label.updateTime} where bl.id=:#{#label.id}")
    int updateLabel(@Param("label") BlogLabel label);

    /**
     * 更新标签状态
     *
     * @return 返回记录数
     */
    @Transactional
    @Modifying
    @Query(value = "update BlogLabel as bl set bl.status=:status where bl.id=:id")
    int updateLabelStatus(Long id, Byte status);

    /**
     * 根据博客编号获取与之关联的所有标签号
     *
     * @param id 博客编号
     * @return 返回标签编号集合
     */
    @Query(value = "SELECT labels_id FROM blog_labels WHERE blogs_id = :id", nativeQuery = true)
    List<Long> getLabelIdByBlogId(@Param("id") Long id);

    /**
     * 获取所有启用状态的标签
     *
     * @return 返回标签集合
     */
    @Query("select bl from BlogLabel as bl where bl.status = :status")
    List<BlogLabel> getLabelsByStatus(@Param("status") Byte status);
}
