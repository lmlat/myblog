package com.aitao.myblog.controller;

import com.aitao.myblog.domain.*;
import com.aitao.myblog.service.*;
import com.aitao.myblog.utils.OssUtils;
import com.aitao.myblog.utils.ResponseCode;
import com.aitao.myblog.utils.StringUtils;
import com.aitao.myblog.utils.ValidUtils;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

/**
 * Author : AiTao
 * Date : 2020/10/29
 * Time : 0:19
 * Information :
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IAdminService adminService;
    @Autowired
    private IAdminLabelManageService adminLabelManageService;
    @Autowired
    private IAdminBlogManageService adminBlogManageService;
    @Autowired
    private IAdminCategoryManageService adminCategoryManageService;
    @Autowired
    private IBannerService bannerService;
    @Autowired
    private IBlogPhotoService blogPhotoService;

    /**
     * @return 加载管理员登录页
     */
    @GetMapping({"", "/"})
    public String loadLogin() {
        return "admin/login";
    }

    /**
     * @return 加载admin文件夹下的所有页面, 不包含子文件夹下的页面
     */
    @GetMapping("/{path}")
    public String loadAdmin(@PathVariable("path") String path,
                            HttpSession session) {
        User user = (User) session.getAttribute("USER");
        if (user != null) {
            return "admin/" + path;
        } else {
            return "admin/login";
        }
    }

    /**
     * 标签管理
     *
     * @return 加载labels-manage文件夹下的所有页面
     */
    @GetMapping("/labels/{path}")
    public String loadLabels(@PathVariable("path") String path) {
        return "admin/labels-manage/" + path;
    }

    /**
     * 新建标签
     *
     * @return
     */
    @PostMapping("/labels/create")
    @ResponseBody
    public String createLabel(@Valid BlogLabel label, BindingResult result) {
        BlogLabel bl = adminLabelManageService.getLabelByName(label.getName());
        if (bl != null) {//标签名已存在
            return "exist";
        }
        if (result.hasErrors()) {
            return "error";
        }
        //初始化标签数据
        label.setCreateTime(new Date());
        label.setUpdateTime(new Date());
        label.setStatus((byte) 1);
        //截取字段
        if (label.getName().length() > 20) {
            label.setName(label.getName().substring(0, 20));
        }
        //持久化标签数据
        adminLabelManageService.saveLabel(label);
        System.out.println(label);
        return "success";
    }

    /**
     * 标签列表
     *
     * @return
     */
    @GetMapping("/labels/list")
    public String labelList(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, RedirectAttributes attributes) {
        //标签分页显示
        Page<BlogLabel> labels = adminLabelManageService.labelOrderByIdAscList(pageable);
        labels.hasPrevious();
        labels.hasNext();
        attributes.addFlashAttribute("labels", labels);
        System.out.println(labels);
        return "redirect:label-list";
//        return "label-list :: label-list";
    }

    @GetMapping("/labels/list/search")
    public String serachLabelList(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable, RedirectAttributes attributes) {
        Page<BlogLabel> labels = adminLabelManageService.labelOrderByIdAscList(pageable);
        attributes.addFlashAttribute("labels", labels);
        System.out.println(labels);
        return "label-list :: label-list";
    }

    /**
     * 删除指定标签
     *
     * @return
     */
    @GetMapping("/labels/del/{param}")
    @ResponseBody
    public String deleteLabel(@PathVariable("param") String param) {
        System.out.println("param:" + param);
        //字符串转数字,保证不抛异常
        Long id = StringUtils.strToLong(param);
        System.out.println(id);
        int record = -1;//记录数
        if (id != null && id != 0L) {
            record = adminLabelManageService.deleteLabelById(id);
        } else if (param != null && !param.equals("")) {
            record = adminLabelManageService.deleteLabelByName(param);
        }
        System.out.println("记录数：" + record);
        return "DELETE_SUCCESS";
    }

    /**
     * 修改标签状态(启用 - 禁用)
     */
    @GetMapping("/labels/update")
    @ResponseBody
    public String updateLabelStatus(BlogLabel label) {
        label.setStatus(label.getStatus() == 1 ? (byte) 0 : (byte) 1);
        label.setUpdateTime(new Date());
        adminLabelManageService.updateLabelStatus(label);
        return "UPDATE_SUCCESS";
    }

    /**
     * 修改标签信息（回显）
     *
     * @return 加载分类编辑页
     */
    @GetMapping("/labels/update/{id}")
    public String editLabel(@PathVariable("id") String id, Model mode) {
        BlogLabel label = adminLabelManageService.getLabelById(StringUtils.strToLong(id));
        mode.addAttribute("label", label);
        return "admin/labels-manage/edit-label";
    }

    /**
     * 编辑标签信息
     */
    @GetMapping("/labels/update/edit")
    @ResponseBody
    public Map<String, Object> updateLabel(BlogLabel label) {
        BlogLabel bl = adminLabelManageService.getLabelByName(label.getName());//根据标签名获取数据
        //判断编辑的标签名是否存在
        if (bl == null || bl.getName().equals(label.getName())) {
            adminLabelManageService.updateLabel(label);
            return ResponseCode.ok("LABEL_EDIT_SUCCESS");
        }
        return ResponseCode.error("LABEL_EDIT_ERROR");
    }

    /**
     * 分类管理
     *
     * @return 加载categorys-manage文件夹下的所有页面
     */
    @GetMapping("/categorys/{path}")
    public String loadCategoryList(@PathVariable("path") String path) {
        return "admin/categorys-manage/" + path;
    }

    /**
     * 新建分类
     *
     * @return
     */
    @PostMapping("/categorys/create")
    @ResponseBody
    public String createategory(@Valid BlogCategory category, BindingResult result) {
        BlogCategory bc = adminCategoryManageService.getCategoryByName(category.getName());
        if (bc != null) {//分类已存在
            return "exist";
        }
        if (result.hasErrors()) {
            return "error";
        }
        //初始化分类数据
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        category.setStatus((byte) 1);
        //截取字段(分类名不能超过20个字符)
        if (category.getName().length() > 20) {
            category.setName(category.getName().substring(0, 20));
        }
        //持久化标签数据
        adminCategoryManageService.saveCategory(category);
        return "success";
    }

    /**
     * 分类列表
     *
     * @return
     */
    @GetMapping("/categorys/list")
    public String categoryList(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.ASC) Pageable pageable, RedirectAttributes attributes) {
        //博客分类分页显示
        Page<BlogCategory> categories = adminCategoryManageService.categoryOrderByIdAscList(pageable);
        attributes.addFlashAttribute("categories", categories);
        System.out.println(categories);
        return "redirect:category-list";
    }

    /**
     * 删除指定分类
     *
     * @return
     */
    @GetMapping("/categorys/del/{id}")
    @ResponseBody
    public String deleteCategory(@PathVariable("id") String param) {
        Long id = StringUtils.strToLong(param);//字符串转数字
        System.out.println(id);
        int record = -1;//记录数
        if (id != null && id != 0L) {
            record = adminCategoryManageService.deleteCategoryById(id);
        } else if (id != null && !id.equals("")) {
            record = adminCategoryManageService.deleteCategoryByName(param);
        }
        System.out.println("记录数：" + record);
        return "DELETE_SUCCESS";
    }

    /**
     * 修改分类状态(启用 - 禁用)
     */
    @GetMapping("/categorys/update")
    @ResponseBody
    public String updateCategoryStatus(BlogCategory category) {
        System.out.println(category);
        category.setStatus(category.getStatus() == 1 ? (byte) 0 : (byte) 1);
        category.setUpdateTime(new Date());
        adminCategoryManageService.updateCategoryStatus(category);
        return "UPDATE_SUCCESS";
    }

    /**
     * 编辑分类信息
     *
     * @return 加载分类编辑页
     */
    @GetMapping("/categorys/update/{id}")
    public String editCategory(@PathVariable("id") String id, Model mode) {
        BlogCategory category = adminCategoryManageService.getCategoryById(StringUtils.strToLong(id));
        mode.addAttribute("category", category);
        return "admin/categorys-manage/edit-category";
    }

    /**
     * 修改分类信息
     */
    @GetMapping("/categorys/update/edit")
    @ResponseBody
    public Map<String, Object> updateCategory(BlogCategory category) {
        BlogCategory bc = adminCategoryManageService.getCategoryByName(category.getName());//根据分类名获取数据
        //判断编辑的分类名是否存在
        if (bc == null || bc.getName().equals(category.getName())) {
            System.out.println(category);
            adminCategoryManageService.updateCategory(category);
            return ResponseCode.ok("CATEGORY_EDIT_SUCCESS");
        }
        return ResponseCode.error("CATEGORY_EDIT_ERROR");
    }

    /**
     * 管理员密码登录
     *
     * @return 加载登录后台页
     */
    @PostMapping("/psdLogin")
    public String psdLogin(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("valid") String valid, HttpSession session, RedirectAttributes attributes) {
        System.out.println("valid:" + valid);
        String generatorValid = (String) session.getAttribute("VALID");
        User user = null;
        //校验验证码
        if (generatorValid != null && generatorValid.equalsIgnoreCase(valid)) {
            user = adminService.getUserByUserNameAndPassword(username, password);//读数据
            System.out.println("psdLogin:" + user);
            //校验用户
            if (user != null && user.getUsername().equals(username) && user.getPassword().equals(password)) {
                session.setAttribute("USER", user);
                return "redirect:index";
            } else {
                attributes.addFlashAttribute("message", "用户不存在");
            }
        } else {
            attributes.addFlashAttribute("message", "验证码输入有误");
        }
        return "redirect:login";
    }

    /**
     * 管理员邮箱登录
     *
     * @return 加载登录后台页
     */
    @PostMapping("/emailLogin")
    public String emailLogin(@RequestParam("email") String email, @RequestParam("valid") String valid, HttpSession session, RedirectAttributes attributes) {
        String generatorValid = (String) session.getAttribute("VALID");
        User user = null;
        //校验验证码
        if (generatorValid != null && generatorValid.equalsIgnoreCase(valid)) {
            user = adminService.getUserByEmail(email);
            System.out.println("emailLogin:" + user);
            //校验用户邮箱
            if (user != null && user.getEmail().equals(email)) {
                session.setAttribute("USER", user);
                return "admin/index";
            } else {
                attributes.addFlashAttribute("message", "用户邮箱不存在");
            }
        } else {
            attributes.addFlashAttribute("message", "验证码输入有误");
        }
        return "redirect:login";
    }


    /**
     * 注销登录
     *
     * @return 加载管理员登录页
     */
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes attributes) {
        session.invalidate();//所以session失效
        return "redirect:admin/login";
    }

    /**
     * 生成验证码
     *
     * @return 返回图片流对象
     */
    @GetMapping("/generateValid")
    @ResponseBody
    public Map<String, Object> generateValid(HttpServletRequest request, HttpServletResponse response) {
        //禁止缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        HttpSession session = request.getSession();
        ValidUtils validUtils = new ValidUtils();
        try {
            validUtils.setOs(response.getOutputStream());
            validUtils.outputImage(validUtils.getWidth(), validUtils.getHeight(), null, true);
            session.setAttribute("VALID", validUtils.getCode());
            System.out.println("验证码：" + validUtils.getCode());
        } catch (IOException e) {
            logger.warn("异常结果:" + e);
        }
        return ResponseCode.ok();
    }

    /**
     * 文章管理
     *
     * @return 加载articles-manage文件夹下的所有页面
     */
    @RequestMapping("/articles/{path}")
    public String loadArticles(@PathVariable("path") String path, HttpSession session, Model model) {
        //写博客
        if (path.equals("write-article")) {
            session.setAttribute("categorys", adminCategoryManageService.categoryOrderByIdDescList());
            session.setAttribute("labels", adminLabelManageService.labelOrderByIdDescList());
            model.addAttribute("blog", new Blog());
        }
        return "admin/articles-manage/" + path;
    }

    /**
     * 写(发布)博客 / 更新博客
     *
     * @return 加载所有文章列表
     */
    @PostMapping("/articles/write")
    public String writeBlog(Blog blog, @RequestParam(value = "cid", required = false) String cid, @RequestParam(value = "tags", required = false) List<String> tags, @RequestParam("saveOrUpdate") Integer saveOrUpdate) {
        Date date = new Date();
        if (blog.getLabels() == null) {
            blog.setLabels(new ArrayList<>());
        }
        //自定义分类
        if (blog.getCategory() == null) {
            blog.setCategory(new BlogCategory());
            if (!Character.isDigit(cid.charAt(0))) {
                blog.getCategory().setName(cid);
                blog.getCategory().setStatus((byte) 1);
                blog.getCategory().setCreateTime(date);
                blog.getCategory().setUpdateTime(date);
                adminCategoryManageService.saveCategory(blog.getCategory());//新增自定义分类
            } else {
                blog.getCategory().setId(Long.valueOf(cid));
            }
        }
        //自定义标签
        for (String tag : tags) {
            BlogLabel bl = new BlogLabel();
            if (!Character.isDigit(tag.charAt(0))) {
                bl.setUpdateTime(date);
                bl.setCreateTime(date);
                bl.setStatus((byte) 1);
                bl.setName(tag);
                bl.setInfo("");
                System.out.println(bl);
                BlogLabel blogLabel = adminLabelManageService.saveLabel(bl);//新增自定义标签
                blog.getLabels().add(blogLabel);
            } else {
                bl.setId(Long.valueOf(tag));
                blog.getLabels().add(adminLabelManageService.getLabelById(bl.getId()));
            }
        }
        //saveOrUpdate = 1更新
        //saveOrUpdate = 0发布
        System.out.println("blog:" + blog);
        blog.setUpdateTime(date);
        if (saveOrUpdate == 1) {
            adminBlogManageService.updateBlog(blog);//更新博客信息
        } else {
            blog.setCreateTime(date);
            blog.setViews(0L);
            List<BlogPhoto> blogPhotos = blogPhotoService.listPhotosByFolder("blog-background");
            blog.setBgImage(blogPhotos.get(new Random().nextInt(blogPhotos.size())).getUrl());
            adminBlogManageService.saveBlog(blog);//发布博客
        }
        return "redirect:list?FLAG=-1";
    }

    /**
     * (全部文章，公开文章，私密文章，草稿箱，回收站)
     *
     * @return 加载所有文章列表
     */
    @GetMapping("/articles/list")
    public String PaginationBlogList(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, @RequestParam("FLAG") Byte flag, RedirectAttributes attributes) {
        Blog blog = new Blog();
        //-1全部文章 1公开 2私密 3草稿 4回收站
        blog.setStatus(flag);
        Page<Blog> blogs = adminBlogManageService.listBlogLimit(blog, pageable);
        attributes.addFlashAttribute("blogs", blogs);
        attributes.addFlashAttribute("FLAG", flag);
        System.out.println("blogs:" + blogs);
        return "redirect:article-list";
    }

    /**
     * 加入回收站
     */
    @PostMapping("/articles/{id}/updateStatus")
    @ResponseBody
    public String updateBlogStatus(@PathVariable("id") Long id) {
        adminBlogManageService.updateBlogStatusById(id);
        return "success";
    }

    /**
     * 删除博客文章
     */
    @PostMapping("/articles/{id}/del")
    @ResponseBody
    public Map<String, Object> delBlog(@PathVariable("id") Long id) {
        adminBlogManageService.deleteById(id);
        return ResponseCode.ok("BLOG_DELETE_SUCCESS");
    }

    /**
     * markdown图片上传
     * {
     * success : 0 | 1, //0表示上传失败;1表示上传成功
     * message : "提示的信息",
     * url     : "图片地址" //上传成功时才返回
     * }
     *
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public JSONObject markdownUpload(@RequestParam("editormd-image-file") MultipartFile file) {
        String fileName = StringUtils.uuid().concat("#").concat(Objects.requireNonNull(file.getOriginalFilename()));
        JSONObject object = new JSONObject();
        try {
            String imgUrl = OssUtils.uploadFile("blog-upload", fileName, file.getInputStream());
            object.put("message", "文件上传成功");
            object.put("success", 1);
            object.put("url", imgUrl.replace("#", "%23"));
        } catch (IOException e) {
            logger.warn("文件上传失败");
        }
        return object;
    }

    /**
     * 编辑博客(回显)
     */
    @GetMapping("/articles/{id}/edit")
    public String editBlog(@PathVariable("id") Long id, Model model) {
        Blog blog = adminBlogManageService.getBlogById(id);
        List<Long> ids = adminLabelManageService.getLabelIdByBlogId(id);//根据博客编号获取与之关联的所有标签号
        blog.setIds(StringUtils.listToString(ids));
        model.addAttribute("blog", blog);
        model.addAttribute("EDIT_STATUS", "YES");//进行编辑状态
        model.addAttribute("blogId", id);//当前编辑的文章编号
        return "admin/articles-manage/write-article";
    }

    /**
     * 用户管理
     *
     * @return 加载user-manage文件夹下的所有页面
     */
    @RequestMapping("/user/{path}")
    public String loadUsers(@PathVariable("path") String path) {
        return "admin/user-manage/" + path;
    }

    /**
     * 轮播图管理
     *
     * @return 加载banner-manage文件夹下的所有页面
     */
    @RequestMapping("/banner/{path}")
    public String loadBanners(@PathVariable("path") String path) {
        return "admin/banner-manage/" + path;
    }

    /**
     * 将博客加入轮播图列表
     *
     * @return
     */
    @GetMapping("/banner/add")
    @ResponseBody
    public Map<String, Object> addBanner() {
        List<Blog> blogs = adminBlogManageService.listBlogLimit8();
        for (Blog b : blogs) {
            //添加到轮播图列表中
            Banner banner = new Banner();
            banner.setCreateTime(b.getCreateTime());
            banner.setUpdateTime(b.getUpdateTime());
            banner.setStatus((byte) 1);
            banner.setKeyword(b.getKeyword());
            banner.setTitle(b.getTitle());
            banner.setUrl(b.getBgImage());
            banner.setBid(b.getId());
            bannerService.saveBanner(banner);
        }
        return ResponseCode.ok();
    }

    @GetMapping("/banner/update")
    public String updateBanner() {
        //1、每隔一段时间，就从数据库中读取8条数据，添到banner中
        return "";
    }
}
