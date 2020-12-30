package com.aitao.myblog.dao;

import com.aitao.myblog.domain.BlogPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author : AiTao
 * @Create : 2020/12/16 16:53
 * @Description : 博客图片持久层接口
 */
public interface IBlogPhotoRepository extends JpaRepository<BlogPhoto, Long>, JpaSpecificationExecutor<BlogPhoto> {
    /**
     * 根据指定相册编号，获取所有图片
     *
     * @param id 相册编号
     * @return 返回相片集合
     */
    @Query(value = "SELECT *FROM photo AS p WHERE p.id IN(SELECT blog_photo_id FROM album_blog_photo WHERE album_id=:id)", nativeQuery = true)
    List<BlogPhoto> listPhotos(@Param("id") Long id);

    /**
     * 根据相片文件夹获取相片的唯一编号
     *
     * @param folder 文件夹目录
     * @return
     */
    @Query(value = "SELECT p.id FROM photo p WHERE p.folder=:folder", nativeQuery = true)
    List<Long> getPhotoIdByFolder(@Param("folder") String folder);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "insert into album_blog_photo(album_id,blog_photo_id) values(?1,?2)")
    int saveAlbumAndPhoto(@Param("albumId") Long albumId, @Param("photoId") Long photoId);

    @Query("select bp from BlogPhoto as bp where bp.folder=?1")
    List<BlogPhoto> listPhotosByFolder(String folder);
}
