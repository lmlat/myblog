package com.aitao.myblog.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author : AiTao
 * @Create : 2020/12/16 0:58
 * @Description : 轮播图管理实体类
 */
@Table(name = "banner")
@Entity
@NoArgsConstructor
@Accessors(chain = true)
public class Banner implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;//轮播编号

    @Getter
    @Setter
    private Long bid;//博客编号

    @Getter
    @Setter
    private String title;//博客标题

    @Getter
    @Setter
    private String keyword;//关键词

    @Getter
    @Setter
    private String url;//轮播图封面地址

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
    private Byte status;//状态(启用1,禁用0)
}
