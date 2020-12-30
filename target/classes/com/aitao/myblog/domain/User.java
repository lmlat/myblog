package com.aitao.myblog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/10/24
 * Time : 20:57
 * Information : 用户实体类
 */
@Entity
@Table(name = "user")
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;//编号

    @Getter
    @Setter
    private String username;//用户名

    @Getter
    @Setter
    private String password;//密码

    @Getter
    @Setter
    private String phone;//手机号

    @Getter
    @Setter
    private String email;//邮箱

    @Getter
    @Setter
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;//创建时间

    @Getter
    @Setter
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updateTime;//更新时间

    @Getter
    @Setter
    private String avatar;//头像

    @Getter
    @Setter
    private boolean status;//用户状态

    @Getter
    @Setter
    private String nickname;//昵称

    @Getter
    @Setter
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Blog> blogs = new ArrayList<>();
}
