package com.aitao.myblog.service;

import com.aitao.myblog.domain.BlogLabel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/11/2
 * Time : 1:38
 * Information :  博客标签业务层接口
 */
public interface IBlogLabelService {
    /**
     * 添加博客标签信息
     *
     * @param label 博客标签信息(博客分类名称不区分大小写)
     * @return 返回博客标签信息
     */
    BlogLabel saveLabel(BlogLabel label);

    /**
     * 根据指定编号获取博客标签信息
     *
     * @param id 博客标签编号
     * @return 返回博客标签信息
     */
    BlogLabel getLabelById(Long id);

    /**
     * 通过博客分类名查找指定的分类信息
     *
     * @param name 博客标签名
     * @return 返回博客标签信息
     */
    BlogLabel getLabelByName(String name);

    /**
     * 根据分类编号删除指定的博客标签信息
     *
     * @param id 标签编号
     */
    void deleteLabel(Long id);

    /**
     * 获取所有博客标签信息(分页显示)
     *
     * @return 返回博客标签集合
     */
    Page<BlogLabel> listLabelLimit(Pageable pageable);

    /**
     * 修改指定博客标签信息
     *
     * @param label 博客标签信息
     * @return 返回博客标签信息
     */
    BlogLabel updateLabel(Long id, BlogLabel label);

    /**
     * 获取所有启用状态的标签
     *
     * @return 返回BlogLabel集合对象
     */
    List<BlogLabel> getLabelsByStatus(Byte status);
}
