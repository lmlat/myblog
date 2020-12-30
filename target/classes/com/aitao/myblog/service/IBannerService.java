package com.aitao.myblog.service;

import com.aitao.myblog.domain.Banner;

import java.util.List;

/**
 * @Author : AiTao
 * @Create : 2020/12/16 20:00
 * @Description : 轮播图列表业务逻辑层接口
 */
public interface IBannerService {
    /**
     * 添加轮播图数据
     *
     * @param banner 轮播图信息
     * @return 返回Banner
     */
    Banner saveBanner(Banner banner);

    /**
     * 轮播图文章(8篇) 根据title排序
     *
     * @return 返回Banner集合
     */
    List<Banner> listBannerBlog();
}
