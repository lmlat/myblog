package com.aitao.myblog.dao;

import com.aitao.myblog.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author : AiTao
 * @Create : 2020/12/22 16:01
 * @Description : 博客评论持久层接口
 */
public interface ICommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
    //根据博客编号获取所有根评论（1级评论）
    @Query(value = "select * from comment where blog_id=:id and status=1 and comment_id is null order by create_time asc", nativeQuery = true)
    List<Comment> listCommentsById(@Param("id") Long id);

    //获取博客编号为null的所有根评论（1级评论）
    @Query(value = "select * from comment where status=1 and comment_id is null and blog_id is null order by create_time asc", nativeQuery = true)
    List<Comment> listCommentsById();

    //获取所有根评论（1级评论）
    @Query(value = "select *from comment where status = 1 and comment_id is null", nativeQuery = true)
    List<Comment> listCommentsNull();

    //评论数统计
    @Query(value = "select count(id) as total from comment where status = 1 and comment_id is null", nativeQuery = true)
    Long countComments();

    //回复数统计
    @Query(value = "select count(id) as total from comment where status = 1 and comment_id is not null", nativeQuery = true)
    Long countReplys();
}
