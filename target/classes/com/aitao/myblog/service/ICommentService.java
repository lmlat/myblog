package com.aitao.myblog.service;

import com.aitao.myblog.domain.Album;
import com.aitao.myblog.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author : AiTao
 * @Create : 2020/12/16 16:57
 * @Description : 博客评论业务层接口
 */
public interface ICommentService {

    /**
     * 根据博客编号获取评论集合及子集
     *
     * @param id 博客编号
     * @return
     */
    List<Comment> listCommentsById(Long id);

    /**
     * 添加评论
     *
     * @param comment 评论信息
     * @return
     */
    Comment saveComment(Comment comment);

    /**
     * 分页展示评论信息
     * @return
     */
    List<Comment> listCommentsLimit();
}
