package com.aitao.myblog.controller;

import com.aitao.myblog.domain.Album;
import com.aitao.myblog.domain.BlogPhoto;
import com.aitao.myblog.service.IAlbumService;
import com.aitao.myblog.service.IBlogPhotoService;
import com.aitao.myblog.utils.OssUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/10/23
 * Time : 23:45
 * Information : 博客个人相册图片控制器
 */
@Controller
public class BlogPhotoController {
    @Autowired
    private IBlogPhotoService blogPhotoService;
    @Autowired
    private IAlbumService albumService;

    /**
     * 加载个人相册图片页
     *
     * @return
     */
    @GetMapping({"/photo", "/photo/"})
    public String photo() {
        return "blog-photo";
    }

    /**
     * 加载指定相册下的所有相片
     *
     * @param id 相册编号
     */
    @GetMapping("/photo/{id}")
    public String loadPhotos(@PathVariable("id") Long id,
                             Model model) {
        Album album = albumService.getAlbumById(id);
        model.addAttribute("photos", blogPhotoService.listPhotos(id));
        model.addAttribute("album", album);
        return "blog-photo";
    }

    /**
     * 将OSS中的图片添加到数据库
     *
     * @return
     */
    @GetMapping("/photo/add/{folder}/{albumId}")
    @ResponseBody
    public List<BlogPhoto> all(@PathVariable("folder") String folder,
                               @PathVariable("albumId") Long albumId) {
        List<BlogPhoto> datas = new ArrayList<>();
        String prefix = "https://lml-bucket.oss-cn-beijing.aliyuncs.com/";
        List<String> avatars = OssUtils.getFilesUrl(folder);//url
        for (String avatar : avatars) {
            BlogPhoto photo = new BlogPhoto();
            photo.setCreateTime(new Date());
            photo.setUpdateTime(new Date());
            photo.setStatus((byte) 1);
            photo.setPrefix(prefix);
            photo.setSuffix(avatar.substring(avatar.indexOf(folder)));
            photo.setFileName(avatar.substring(avatar.indexOf(folder) + (folder.length() + 1)));//avatar/
            photo.setUrl(avatar);
            photo.setFolder(folder);
            blogPhotoService.savePhoto(photo);
            datas.add(photo);
        }
        List<Long> photoIdByFolder = blogPhotoService.getPhotoIdByFolder(folder);
        for (int i = 0; i < photoIdByFolder.size(); i++) {
            blogPhotoService.saveAlbumAndPhoto(albumId, photoIdByFolder.get(i));
        }
        return datas;
    }
}
