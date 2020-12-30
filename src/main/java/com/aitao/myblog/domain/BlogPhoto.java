package com.aitao.myblog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/10/24
 * Time : 18:47
 * Information : 博客照片
 */
@Table(name = "photo")
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class BlogPhoto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;//图片编号

    @Getter
    @Setter
    private String prefix;//前缀

    @Getter
    @Setter
    private String suffix;//后缀

    @Getter
    @Setter
    private String folder;//文件夹名

    @Getter
    @Setter
    private String fileName;//文件全名

    @Getter
    @Setter
    private String url;//照片地址

    @ManyToMany(mappedBy = "blogPhoto")
    @Getter
    @Setter
    @JsonIgnore
    private List<Album> album;

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
