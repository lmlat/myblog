package com.aitao.myblog.service.impl;

import com.aitao.myblog.dao.IAlbumRepository;
import com.aitao.myblog.domain.Album;
import com.aitao.myblog.service.IAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : AiTao
 * @Create : 2020/12/22 13:21
 * @Description : 博客个人相册业务层接口实现类
 */
@Service
public class AlbumServiceImpl implements IAlbumService {
    @Autowired
    private IAlbumRepository albumRepository;

    /**
     * 创建相册
     *
     * @param album 相册信息
     * @return 返回Album对象
     */
    @Override
    public Album saveAlbum(Album album) {
        return albumRepository.save(album);
    }

    /**
     * 删除相册
     *
     * @param id 编号
     */
    @Override
    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }

    /**
     * 获取所有启用状态的相册
     *
     * @return 返回Album集合
     */
    @Override
    public List<Album> listAlbums() {
        return albumRepository.findAll(new Specification<Album>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Album> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //定义条件集合
                List<Predicate> predicates = new ArrayList<>();
                //添加条件 where status = 1
                predicates.add(criteriaBuilder.equal(root.get("status").as(Byte.class), (byte) 1));
                //定义满足条件数组
                Predicate[] predicateArr = new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(predicateArr)).getRestriction();
            }
        });
    }

    @Override
    public Page<Album> listAlbumsLimit(Pageable pageable) {
        return albumRepository.findAll(pageable);
    }

    @Override
    public Album getAlbumById(Long id) {
        return albumRepository.getOne(id);
    }
}
