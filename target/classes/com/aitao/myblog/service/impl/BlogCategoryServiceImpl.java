package com.aitao.myblog.service.impl;

import com.aitao.myblog.config.NotFoundExceptionHandler;
import com.aitao.myblog.dao.IBlogCategoryRepository;
import com.aitao.myblog.domain.BlogCategory;
import com.aitao.myblog.service.IBlogCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/11/2
 * Time : 1:38
 * Information : 博客分类业务层接口实现类
 */
@Service
public class BlogCategoryServiceImpl implements IBlogCategoryService {
    //增、删、改都使用事务
    private Logger logger = LoggerFactory.getLogger(BlogCategoryServiceImpl.class);
    @Autowired
    private IBlogCategoryRepository blogCategoryRepository;

    @Transactional
    @Override
    public BlogCategory saveCategory(BlogCategory category) {
        return blogCategoryRepository.save(category);
    }

    @Override
    public BlogCategory getCategoryById(Long id) {
        return blogCategoryRepository.getOne(id);
    }

    @Override
    public BlogCategory getCategoryByName(String name) {
        return blogCategoryRepository.getCategoryByName(name);
    }

    @Override
    public Page<BlogCategory> listCategoryLimit(Pageable pageable) {
        return blogCategoryRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public void deleteCategory(Long id) {
        blogCategoryRepository.deleteById(id);
    }

    @Transactional
    @Override
    public BlogCategory updateCategory(Long id, BlogCategory category) {
        //通过id获取数据库中指定的分类信息
        BlogCategory bc = blogCategoryRepository.getOne(id);
        if (bc == null) {
            logger.warn("博客分类数据不存在~~");
            return null;
        }
        //将category中的数据拷贝到bc中
        BeanUtils.copyProperties(category, bc);
        //更新数据
        return blogCategoryRepository.save(bc);
    }
    /**
     * 获取所有启用状态的分类
     *
     * @return 返回BlogCategory集合对象
     */
    @Override
    public List<BlogCategory> getCategoryByStatus(Byte status) {
        return blogCategoryRepository.getCategoryByStatus(status);
    }
}
