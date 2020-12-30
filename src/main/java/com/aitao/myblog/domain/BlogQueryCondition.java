package com.aitao.myblog.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author : AiTao
 * @Create : 2020/12/18 2:01
 * @Description : 博客查询对象
 */
@NoArgsConstructor
@Accessors(chain = true)
public class BlogQueryCondition {
    @Getter
    @Setter
    private Date startTime;//开始时间

    @Getter
    @Setter
    private Date endTime;//结束时间

    @Getter
    @Setter
    private String keyword;//关键词
}
