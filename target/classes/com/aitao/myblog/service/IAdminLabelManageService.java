package com.aitao.myblog.service;

import com.aitao.myblog.domain.BlogLabel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/11/6
 * Time : 23:56
 * Information : 标签管理业务层接口
 */
public interface IAdminLabelManageService {
    /**
     * 新建标签
     *
     * @param label 标签对象
     * @return 返回记录数
     */
    BlogLabel saveLabel(BlogLabel label);

    /**
     * 标签列表(降序排序)
     *
     * @return 返回所有标签集合
     */
    List<BlogLabel> labelOrderByIdDescList();

    /**
     * 标签列表(升序排序)
     *
     * @return 返回所有标签集合
     */
    Page<BlogLabel> labelOrderByIdAscList(Pageable pageable);

    /**
     * 根据指定标签编号查找标签
     *
     * @param id 标签编号
     * @return 返回标签对象
     */
    BlogLabel getLabelById(Long id);

    /**
     * 根据指定标签名查找标签
     *
     * @param name 标签名
     * @return 返回标签对象
     */
    BlogLabel getLabelByName(String name);

    /**
     * 根据博客编号获取与之关联的所有标签号
     * @param id 博客编号
     * @return 返回标签编号集合
     */
    List<Long> getLabelIdByBlogId(Long id);
    /**
     * 删除指定编号的标签
     *
     * @param id 标签编号
     * @return 1删除成功 0删除失败
     */
    int deleteLabelById(Long id);

    /**
     * 删除指定标签名的标签
     *
     * @param name 标签名
     * @return 1删除成功 0删除失败
     */
    int deleteLabelByName(String name);

    /**
     * 更新标签信息
     *
     * @param label 标签对象
     * @return 返回记录数
     */
    BlogLabel updateLabel(BlogLabel label);

    /**
     * 修改标签状态
     *
     * @param label 标签信息
     * @return 返回记录数
     */
    int updateLabelStatus(BlogLabel label);
}
