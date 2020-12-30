package com.aitao.myblog.service.impl;

import com.aitao.myblog.dao.IBannerRepository;
import com.aitao.myblog.domain.Banner;
import com.aitao.myblog.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : AiTao
 * @Create : 2020/12/16 20:00
 * @Description : 轮播图列表业务逻辑层接口实现类
 */
@Service
public class BannerServiceImpl implements IBannerService {
    @Autowired
    private IBannerRepository bannerRepository;

    /**
     * 添加轮播图数据
     *
     * @param banner 轮播图信息
     * @return 返回Banner "0 0 0 ? * FRI"
     */
    @Override
    public Banner saveBanner(Banner banner) {
        return bannerRepository.save(banner);
    }

    /**
     * 轮播图文章(8篇) 根据title排序
     *
     * @return 返回Banner集合
     */
    @Override
    public List<Banner> listBannerBlog() {
        return bannerRepository.listBannerBlog();
    }
}
