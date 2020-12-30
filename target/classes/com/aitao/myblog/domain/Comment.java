package com.aitao.myblog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/10/24
 * Time : 20:40
 * Information : 博客评论实体类
 */
@Entity
@Table(name = "comment")
@NoArgsConstructor
@Accessors(chain = true)
//解决对象是null值没有获取到，无法反序列化为json的问题
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
//@JsonIgnore：直接忽略某个属性，以断开无限递归，序列化或反序列化均忽略。
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String content;//评论内容

    @Getter
    @Setter
    private String nickname;//评论昵称

    @Getter
    @Setter
    private String email;//评论邮箱

    @Getter
    @Setter
    private String avatar;//评论头像

    @Getter
    @Setter
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;//评论时间

    @Getter
    @Setter
    @ManyToOne
    private Blog blog;

    //评论自关联
    //一个评论可以有多条回复
    @OneToMany(mappedBy = "comment")
    @Getter
    @Setter
    @JsonIgnore
    private List<Comment> replyComments = new ArrayList<>();

    //只能回复当前的一条评论
    @ManyToOne
    @Getter
    @Setter
    private Comment comment;

    @Getter
    @Setter
    private Byte status;//状态(启用1,禁用0)

    @Getter
    @Setter
    private Byte adminStatus;//是否为管理员(1是,0不是)
}
