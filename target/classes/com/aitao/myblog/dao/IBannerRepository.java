package com.aitao.myblog.dao;

import com.aitao.myblog.domain.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author : AiTao
 * @Create : 2020/12/16 20:01
 * @Description : 轮播图列表持久层接口
 */
public interface IBannerRepository extends JpaRepository<Banner, Long>, JpaSpecificationExecutor<Banner> {
    //轮播图文章(8篇) 根据title排序
    @Query(value = "SELECT *FROM banner AS b WHERE b.status=1 ORDER BY b.update_time AND b.title DESC LIMIT 8", nativeQuery = true)
    List<Banner> listBannerBlog();

    //需要轮播的一条博客数据(定时更新)
    //int updateBannerBlog();
}
