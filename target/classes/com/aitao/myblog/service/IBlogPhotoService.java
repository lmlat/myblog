package com.aitao.myblog.service;

import com.aitao.myblog.domain.BlogPhoto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author : AiTao
 * @Create : 2020/12/16 16:57
 * @Description : 博客图片业务层接口
 */
public interface IBlogPhotoService {

    /**
     * 图片上传
     * @param photo 图片信息
     * @return 返回BlogPhoto对象
     */
    BlogPhoto savePhoto(BlogPhoto photo);

    /**
     * 分页显示所有图片数据
     * @param pageable 分页对象
     * @return 返回数据集合
     */
    Page<BlogPhoto> listPhotoLimit(Pageable pageable);

    /**
     * 根据指定相册编号，获取所有图片
     * @param id 相册编号
     * @return 返回相片集合
     */
    List<BlogPhoto> listPhotos(Long id);

    /**
     * 根据相片文件夹获取相片的唯一编号
     * @param folder 文件夹目录
     * @return
     */
    List<Long> getPhotoIdByFolder(String folder);

    /**
     * 关联相册和相片
     * @param albumId 相册编号
     * @param photoId 相片编号
     * @return 返回记录数
     */
    int saveAlbumAndPhoto(Long albumId, Long photoId);


    List<BlogPhoto> listPhotosByFolder(String s);
}
