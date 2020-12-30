package com.aitao.myblog.service.impl;

import com.aitao.myblog.config.NotFoundExceptionHandler;
import com.aitao.myblog.dao.IBlogCategoryRepository;
import com.aitao.myblog.dao.IBlogLabelRepository;
import com.aitao.myblog.domain.BlogCategory;
import com.aitao.myblog.domain.BlogLabel;
import com.aitao.myblog.service.IBlogCategoryService;
import com.aitao.myblog.service.IBlogLabelService;
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
public class BlogLabelServiceImpl implements IBlogLabelService {
    //增、删、改都使用事务
    private Logger logger = LoggerFactory.getLogger(BlogLabelServiceImpl.class);
    @Autowired
    private IBlogLabelRepository blogLabelRepository;

    @Transactional
    @Override
    public BlogLabel saveLabel(BlogLabel label) {
        return blogLabelRepository.save(label);
    }

    @Override
    public Page<BlogLabel> listLabelLimit(Pageable pageable) {
        return blogLabelRepository.findAll(pageable);
    }

    @Override
    public BlogLabel getLabelById(Long id) {
        return blogLabelRepository.getOne(id);
    }

    @Transactional
    @Override
    public BlogLabel getLabelByName(String name) {
        return blogLabelRepository.getLabelByName(name);
    }

    @Transactional
    @Override
    public void deleteLabel(Long id) {
        blogLabelRepository.deleteById(id);
    }

    @Transactional
    @Override
    public BlogLabel updateLabel(Long id, BlogLabel label) {
        BlogLabel bl = getLabelById(id);
        if (bl == null) {
            logger.warn("博客标签数据不存在~~");
            return null;
        }
        BeanUtils.copyProperties(label, bl);
        return blogLabelRepository.save(bl);
    }

    /**
     * 获取所有启用状态的标签
     * @return
     */
    @Override
    public List<BlogLabel> getLabelsByStatus(Byte status) {
        return blogLabelRepository.getLabelsByStatus(status);
    }
}
