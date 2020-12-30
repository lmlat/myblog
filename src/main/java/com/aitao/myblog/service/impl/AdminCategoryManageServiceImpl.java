package com.aitao.myblog.service.impl;

import com.aitao.myblog.dao.IBlogCategoryRepository;
import com.aitao.myblog.domain.BlogCategory;
import com.aitao.myblog.service.IAdminCategoryManageService;
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
 * Date : 2020/11/7
 * Time : 0:18
 * Information : 分类管理业务层接口实现类
 */
@Service
public class AdminCategoryManageServiceImpl implements IAdminCategoryManageService {
    @Autowired
    private IBlogCategoryRepository blogCategoryRepository;

    @Override
    public BlogCategory saveCategory(BlogCategory category) {
        return blogCategoryRepository.save(category);
    }

    @Override
    public List<BlogCategory> categoryOrderByIdDescList() {
        return blogCategoryRepository.findAll(new Specification<BlogCategory>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();//创建一个条件集合
                predicates.add(criteriaBuilder.equal(root.get("status").as(Byte.class), (byte) 1));
                Predicate[] predicatesArr = new Predicate[predicates.size()];//创建一个满足条件个数的数组
                return criteriaQuery.where(predicates.toArray(predicatesArr)).getRestriction();//返回拼接好的查询条件
            }
        },Sort.by(Sort.Direction.DESC,"updateTime"));
    }

    @Override
    public Page<BlogCategory> categoryOrderByIdAscList(Pageable pageable) {
        return blogCategoryRepository.findAll(pageable);
    }

    @Override
    public int deleteCategoryById(Long id) {
        return blogCategoryRepository.deleteCategoryById(id);
    }

    @Override
    public int deleteCategoryByName(String name) {
        return blogCategoryRepository.deleteCategoryByName(name);
    }

    @Override
    public BlogCategory updateCategory(BlogCategory category) {
        BlogCategory bc = blogCategoryRepository.getOne(category.getId());
        if (bc == null) {
            return null;
        }
        category.setUpdateTime(new Date());
        category.setCreateTime(bc.getCreateTime());
        category.setStatus(bc.getStatus());
        return blogCategoryRepository.save(category);
    }

    @Override
    public BlogCategory getCategoryByName(String name) {
        return blogCategoryRepository.getCategoryByName(name);
    }

    @Override
    public BlogCategory getCategoryById(Long id) {
        return blogCategoryRepository.getOne(id);
    }

    @Override
    public int updateCategoryStatus(BlogCategory category) {
        return blogCategoryRepository.updateCategoryStatus(category.getId(), category.getStatus());
    }
}
