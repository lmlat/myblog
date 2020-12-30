package com.aitao.myblog.service.impl;

import com.aitao.myblog.dao.IBlogLabelRepository;
import com.aitao.myblog.domain.BlogLabel;
import com.aitao.myblog.service.IAdminLabelManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/11/6
 * Time : 23:58
 * Information : 标签管理业务层接口实现类
 */
@Service
public class AdminLabelManageServiceImpl implements IAdminLabelManageService {
    @Autowired
    private IBlogLabelRepository blogLabelRepository;

    /**
     * 新建标签
     *
     * @param label 标签对象
     * @return 返回记录数
     */
    @Override
    public BlogLabel saveLabel(BlogLabel label) {
        return blogLabelRepository.save(label);
    }

    /**
     * 根据指定标签名查找标签
     *
     * @param name 标签名
     * @return 返回标签对象
     */
    @Override
    public BlogLabel getLabelByName(String name) {
        return blogLabelRepository.getLabelByName(name);
    }

    /**
     * 根据博客编号获取与之关联的所有标签号
     * @param id 博客编号
     * @return 返回标签编号集合
     */
    @Override
    public List<Long> getLabelIdByBlogId(Long id) {
        return blogLabelRepository.getLabelIdByBlogId(id);
    }

    /**
     * 根据指定标签编号查找标签
     *
     * @param id 标签编号
     * @return 返回标签对象
     */
    @Override
    public BlogLabel getLabelById(Long id) {
        return blogLabelRepository.getOne(id);
    }

    /**
     * 标签列表(降序排序)
     *
     * @return 返回所有标签集合
     */
    @Override
    public List<BlogLabel> labelOrderByIdDescList() {
        return blogLabelRepository.findAll(new Specification<BlogLabel>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<BlogLabel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();//定义条件集合
                predicates.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 1));//添加条件
                Predicate[] predicatesArr = new Predicate[predicates.size()];//定义满足条件的数组
                return criteriaQuery.where(predicates.toArray(predicatesArr)).getRestriction();
            }
        }, Sort.by(Sort.Direction.DESC, "updateTime"));
    }

    /**
     * 标签列表(升序排序)
     *
     * @return 返回所有标签集合
     */
    @Override
    public Page<BlogLabel> labelOrderByIdAscList(Pageable pageable) {
        return blogLabelRepository.findAll(pageable);
    }

    /**
     * 删除指定编号的标签
     *
     * @param id 标签编号
     */
    @Override
    public int deleteLabelById(Long id) {
        return blogLabelRepository.deleteLabelById(id);
    }

    /**
     * 删除指定标签名的标签
     *
     * @param name 标签名
     */
    @Override
    public int deleteLabelByName(String name) {
        return blogLabelRepository.deleteLabelByName(name);
    }

    /**
     * 更新标签状态
     *
     * @param label 标签对象
     */
    @Override
    public BlogLabel updateLabel(BlogLabel label) {
        BlogLabel bl = blogLabelRepository.getOne(label.getId());
        if (bl == null) {
            return null;
        }
        label.setUpdateTime(new Date());
        label.setCreateTime(bl.getCreateTime());
        label.setStatus(bl.getStatus());
        return blogLabelRepository.save(label);
    }

    /**
     * 修改标签状态
     *
     * @param label 标签信息
     * @return 返回记录数
     */
    @Override
    public int updateLabelStatus(BlogLabel label) {
        return blogLabelRepository.updateLabelStatus(label.getId(), label.getStatus());
    }
}
