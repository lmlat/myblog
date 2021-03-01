package com.aitao.myblog.service.impl;

import com.aitao.myblog.dao.ICommentRepository;
import com.aitao.myblog.domain.Comment;
import com.aitao.myblog.service.ICommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author : AiTao
 * @Create : 2020/12/16 16:57
 * @Description : 博客评论业务层接口实现类
 */
@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private ICommentRepository commentRepository;

    /**
     * 根据博客编号获取评论信息
     *
     * @param id 编号
     * @return
     */
    @Override
    public List<Comment> listCommentsById(Long id) {
        List<Comment> comments = commentRepository.listCommentsById(id);//获取所有根评论信息
        return iteratorRootComment(comments);//遍历所有根评论对象下的子评论对象
    }

    /**
     * 根据博客编号获取评论信息
     * blog.id = null
     * @return
     */
    @Override
    public List<Comment> listCommentsById() {
        List<Comment> comments = commentRepository.listCommentsById();//获取所有根评论信息
        return iteratorRootComment(comments);//遍历所有根评论对象下的子评论对象
    }

    /**
     * 添加评论
     *
     * @param comment 评论信息
     * @return
     */
    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getComment().getId();//默认值为-1
        if (parentCommentId != -1) {
            //根据父级评论编号，获取子集评论
            comment.setComment(commentRepository.getOne(parentCommentId));
        } else {
            comment.setComment(null);
        }
        comment.setCreateTime(new Date());
        comment.setStatus((byte) 1);
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> listCommentsLimit() {
        //获取评论表中所有根评论
        List<Comment> comments = commentRepository.listCommentsNull();
        return iteratorRootComment(comments);
    }

    //统计评论数
    @Override
    public Long countComments() {
        return commentRepository.countComments();
    }

    //统计回复数
    @Override
    public Long countReplys() {
        return commentRepository.countReplys();
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 遍历每一个根评论节点
     *
     * @return
     */
    private List<Comment> iteratorRootComment(List<Comment> comments) {
        List<Comment> result = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            //将comment中的数据拷贝到c中
            BeanUtils.copyProperties(comment, c);
            System.out.println("c:" + c);
            result.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(result);
        return result;
    }

    /**
     * 合并评论的各层子代到第一级子代集合中
     *
     * @param comments root根节点，blog不为空的对象集合
     */
    private void combineChildren(List<Comment> comments) {
        for (Comment comment : comments) {
            List<Comment> reply = comment.getReplyComments();
            for (Comment r : reply) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(r);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    /**
     * 递归迭代遍历子代集合
     *
     * @param comment 被迭代的对象
     */
    private void recursively(Comment comment) {
        //根节点添加到临时存放区
        tempReplys.add(comment);
        if (comment.getReplyComments().size() > 0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size() > 0) {
                    recursively(reply);
                }
            }
        }
    }
}
