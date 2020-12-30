package com.aitao.myblog.dao;

import com.aitao.myblog.domain.Banner;
import com.aitao.myblog.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/11/7
 * Time : 15:39
 * Information : 博客详情持久层接口
 */
public interface IBlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
    //加入回收站
    @Transactional
    @Modifying
    @Query(value = "update Blog as b set b.status=4 where b.id=:id")
    int updateBlogStatusById(@Param("id") Long id);

    //推荐文章(6篇)
    @Query(value = "SELECT *FROM blog AS b WHERE b.recommend_status=TRUE AND b.status=1 ORDER BY b.update_time DESC LIMIT 6", nativeQuery = true)
    List<Blog> listRecommendBlog();

    //文章(8篇) 根据title排序
    @Query(value = "SELECT *FROM blog AS b WHERE b.status=1 ORDER BY b.title DESC LIMIT 8", nativeQuery = true)
    List<Blog> listBlogLimit8();

    //更新博客浏览数
    @Transactional
    @Modifying
    @Query("update Blog as b set b.views=b.views+1 where b.id=:id")
    int updateBlogViews(@Param("id") Long id);

    //关键字博客
    @Query("select b from Blog as b where b.status=1 and b.keyword like :keyword or b.content like :keyword")
    List<Blog> listKeywordBlog(String keyword);

    //统计访问量
    @Query(value = "select sum(views) from blog",nativeQuery = true)
    Long countVisits();

}
