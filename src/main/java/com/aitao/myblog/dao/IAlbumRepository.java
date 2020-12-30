package com.aitao.myblog.dao;

import com.aitao.myblog.domain.Album;
import com.aitao.myblog.domain.BlogPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author : AiTao
 * @Create : 2020/12/16 16:53
 * @Description : 博客个人相册持久层接口
 */
public interface IAlbumRepository extends JpaRepository<Album, Long>, JpaSpecificationExecutor<Album>  {

}
