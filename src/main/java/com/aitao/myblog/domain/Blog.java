package com.aitao.myblog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/10/24
 * Time : 13:08
 * Information : 博客详情实体类
 */
@Entity
@Table(name = "blog")
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@ToString
public class Blog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;//博客编号

    @Getter
    @Setter
    private String title;// 博客标题

    @Getter
    @Setter
    private String content;//博客内容

    @Getter
    @Setter
    private String keyword;//关键词

    @Getter
    @Setter
    private String bgImage;//博客封面图

    @Getter
    @Setter
    private Byte flag;//博客类型名（原创、转载、翻译）

    @Temporal(value = TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date createTime;//创建时间

    @Temporal(value = TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date updateTime;//更新时间

    @Getter
    @Setter
    private Long views;//博客浏览数

    @Getter
    @Setter
    private boolean commentStatus;//1开启评论 0关闭评论

    @Getter
    @Setter
    private boolean issueStatus;//1发布 0未发布(保存)

    @Getter
    @Setter
    private boolean recommendStatus;//开启推荐(1开启 0关闭)

    @Getter
    @Setter
    private boolean rewardStatus;//开启打赏(1开启 0关闭)

    @Getter
    @Setter
    private boolean copyrightStatus;//开启版权(1开启 0关闭)

    @Getter
    @Setter
    private boolean bannerStatus;//开启轮播图(1开启 0关闭)

    @Getter
    @Setter
    private Byte status;//1公开 2私密 3草稿

    @Transient
    @Getter
    @Setter
    private String ids;

    //分类
    @ManyToOne
    @Getter
    @Setter
    private BlogCategory category;

    //级联新增
    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @Getter
    @Setter
    private List<BlogLabel> labels;

    @Getter
    @Setter
    private String autograph;//个性签名

    @ManyToOne
    @Getter
    @Setter
    private User user;

    @OneToMany(mappedBy = "blog")
    @JsonIgnore
    @Getter
    @Setter
    private List<Comment> comments = new ArrayList<>();
}
