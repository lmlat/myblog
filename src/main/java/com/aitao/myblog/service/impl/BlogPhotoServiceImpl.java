package com.aitao.myblog.service.impl;

import com.aitao.myblog.dao.IBlogPhotoRepository;
import com.aitao.myblog.domain.BlogPhoto;
import com.aitao.myblog.service.IBlogPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : AiTao
 * @Create : 2020/12/16 16:57
 * @Description : 博客图片业务层接口实现类
 */
@Service
public class BlogPhotoServiceImpl implements IBlogPhotoService {
    @Autowired
    private IBlogPhotoRepository blogPhotoRepository;

    /**
     * 上传图片
     *
     * @param photo 图片信息
     * @return 返回BlogPhoto对象
     */
    @Override
    public BlogPhoto savePhoto(BlogPhoto photo) {
        return blogPhotoRepository.save(photo);
    }

    /**
     * 分页显示所有图片数据
     *
     * @param pageable 分页对象
     * @return 返回数据集合
     */
    @Override
    public Page<BlogPhoto> listPhotoLimit(Pageable pageable) {
        return blogPhotoRepository.findAll(pageable);
    }

    /**
     * 根据指定相册编号，获取所有图片
     *
     * @param id 相册编号
     * @return 返回相片集合
     */
    @Override
    public List<BlogPhoto> listPhotos(Long id) {
        return blogPhotoRepository.listPhotos(id);
    }

    /**
     * 根据相片文件夹获取相片的唯一编号
     *
     * @param folder 文件夹目录
     * @return
     */
    @Override
    public List<Long> getPhotoIdByFolder(String folder) {
        return blogPhotoRepository.getPhotoIdByFolder(folder);
    }

    @Override
    public int saveAlbumAndPhoto(Long albumId, Long photoId) {
        return blogPhotoRepository.saveAlbumAndPhoto(albumId, photoId);
    }

    @Override
    public List<BlogPhoto> listPhotosByFolder(String folder) {
        return blogPhotoRepository.listPhotosByFolder(folder);
    }
}
