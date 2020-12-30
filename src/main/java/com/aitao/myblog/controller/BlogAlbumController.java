package com.aitao.myblog.controller;

import com.aitao.myblog.domain.Album;
import com.aitao.myblog.domain.BlogPhoto;
import com.aitao.myblog.service.IAlbumService;
import com.aitao.myblog.service.IBlogPhotoService;
import com.aitao.myblog.utils.OssUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/10/23
 * Time : 23:45
 * Information : 博客个人相册控制器
 */
@Controller
@RequestMapping("/album")
public class BlogAlbumController {
    @Autowired
    private IAlbumService albumService;

    /**
     * 加载个人相册页
     *
     * @return
     */
    @RequestMapping({"", "/"})
    public String album(@PageableDefault(size = 7, sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        List<Album> albums = albumService.listAlbums();
        model.addAttribute("albums", albums);
        System.out.println(albums);
        return "blog-album";
    }

    /**
     * 创建相册
     *
     * @param album 相册信息
     */
    @PostMapping("/create")
    public String createAlbum(Album album) {
        albumService.saveAlbum(album);
        return "";
    }
}
