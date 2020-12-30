package com.aitao.myblog.service;

import com.aitao.myblog.domain.Album;
import com.aitao.myblog.domain.BlogPhoto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author : AiTao
 * @Create : 2020/12/16 16:57
 * @Description : 博客个人相册业务层接口
 */
public interface IAlbumService {
    /**
     * 创建相册
     *
     * @param album 相册信息
     * @return 返回Album对象
     */
    Album saveAlbum(Album album);

    /**
     * 删除相册
     *
     * @param id 编号
     */
    void deleteAlbum(Long id);

    /**
     * 获取所有启用状态的相册
     *
     * @return 返回Album集合
     */
    List<Album> listAlbums();


    /**
     * 获取启用状态的相册(个)
     * @param pageable 分页对象
     * @return 返回分页集合对象
     */
    Page<Album> listAlbumsLimit(Pageable pageable);

    /**
     * 获取相册信息
     * @param id 相册编号
     * @return 返回Album
     */
    Album getAlbumById(Long id);
}
