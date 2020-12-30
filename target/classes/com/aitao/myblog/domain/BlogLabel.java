package com.aitao.myblog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/10/24
 * Time : 18:47
 * Information : 博客标签实体类
 */
@Entity
@Table(name = "label")
@Validated
@DynamicUpdate //动态更新
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class BlogLabel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;
    @NotBlank(message = "标签名称不能为空")
    @Getter
    @Setter
    private String name;//标签名
    @Temporal(value = TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date createTime;
    @Temporal(value = TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date updateTime;
    @Getter
    @Setter
    private Byte status;
    @Getter
    @Setter
    private String info;

    @ManyToMany(mappedBy = "labels")
    @JsonIgnore
    @Getter
    @Setter
    private List<Blog> blogs = new ArrayList<>();
}
