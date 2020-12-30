package com.aitao.myblog.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @Author : AiTao
 * @Create : 2020/12/16 11:11
 * @Description : 相册实体类
 */
@Table(name = "album")
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;//相册编号

    @Getter
    @Setter
    private String name;//相册名

    @Getter
    @Setter
    private String url;//封面图

    @ManyToMany
    @Getter
    @Setter
    private List<BlogPhoto> blogPhoto;//图片集

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
