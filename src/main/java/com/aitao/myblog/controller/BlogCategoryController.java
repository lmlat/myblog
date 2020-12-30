package com.aitao.myblog.controller;

import com.aitao.myblog.domain.BlogCategory;
import com.aitao.myblog.domain.BlogLabel;
import com.aitao.myblog.service.IBlogCategoryService;
import com.aitao.myblog.utils.ResponseCode;
import com.aitao.myblog.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Author : AiTao
 * Date : 2020/10/23
 * Time : 23:45
 * Information : 博客标签页控制器
 */
@Controller
@RequestMapping("/category")
public class BlogCategoryController {
    @Autowired
    private IBlogCategoryService blogCategoryService;

    /**
     * 加载博客分类页
     *
     * @return
     */
    @GetMapping({"", "/"})
    public String category(@RequestParam(value = "categoryId", required = false) String categoryId,
                           Model model) {
        List<BlogCategory> categorys = blogCategoryService.getCategoryByStatus((byte) 1);
        if (categoryId != null) {
            model.addAttribute("categoryId", categoryId);
        }
        model.addAttribute("categorys", categorys);
        return "blog-category";
    }

    /**
     * 加载选中人分类下的所有博客数据
     *
     * @return
     */
    @GetMapping("/select/{id}")//category/random/3
    @ResponseBody
    public Map<String, Object> selectCategory(@PathVariable("id") String id) {
        Long no = StringUtils.strToLong(id);
        BlogCategory category = blogCategoryService.getCategoryById(no);
        System.out.println(category);
        return ResponseCode.ok(category.getBlogs());
    }


//    /**
//     * 分页显示
//     *
//     * @param pageable 分页对象
//     * @return 加载博客分类管理页
//     * @PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.DESC)
//     * 每3条数据为1页，根据id按照倒序的方式排序
//     */
//    @GetMapping("/categorys")
//    public String categorys(@PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
//                            Model model) {
//        model.addAttribute("pageable", blogCategoryService.listCategoryLimit(pageable));
//        return "admin/blog-category-manager";
//    }
//
//    @GetMapping("/category/add")
//    public String addCategory(Model model) {
//        model.addAttribute("CATEGORY", new BlogCategory());
//        return "admin/add-category";
//    }
//
//    @GetMapping("/category/{id}/add")
//    public String editCategory(@PathVariable("id") Long id,
//                               Model model) {
//        model.addAttribute("CATEGORY", blogCategoryService.getCategoryById(id));
//        return "admin/add-category";
//    }
//
//    @GetMapping("/category/{id}/delete")
//    public String deleteCategory(@PathVariable("id") Long id,
//                                 RedirectAttributes attributes) {
//        blogCategoryService.deleteCategory(id);
//        attributes.addFlashAttribute("message", "删除成功");
//        return "redirect:/admin/categorys";
//    }
//
//    @PostMapping("/categorys")
//    public String postCategorys(@Valid BlogCategory category,
//                                BindingResult result,
//                                RedirectAttributes attributes) {
//        //根据分类名查找分类字段
//        BlogCategory blogCategory = blogCategoryService.getCategoryByName(category.getName());
//        if (blogCategory != null) {
//            result.rejectValue("name", "nameError", "不能添加重复的分类");
//        }
//        //若JSR303有错误，则跳转到add-category.html页面
//        if (result.hasErrors()) {
//            return "/admin/add-category";
//        }
//        BlogCategory bg = blogCategoryService.saveCategory(category);
//        if (bg == null) {
//            attributes.addFlashAttribute("message", "操作失败");
//        } else {
//            attributes.addFlashAttribute("message", "操作成功");
//        }
//        return "redirect:/admin/categorys";
//    }
//
//    @PostMapping("/categorys/{id}")
//    public String editPostCategorys(@Valid BlogCategory category,
//                                    @PathVariable("id") Long id,
//                                    BindingResult result,
//                                    RedirectAttributes attributes) {
//        BlogCategory blogCategory = blogCategoryService.getCategoryByName(category.getName());
//        if (blogCategory != null) {
//            result.rejectValue("name", "nameError", "不能添加重复的分类");
//        }
//        if (result.hasErrors()) {
//            return "/admin/add-category";
//        }
//        BlogCategory bg = blogCategoryService.updateCategory(id, category);
//        if (bg == null) {
//            attributes.addFlashAttribute("message", "更新失败");
//        } else {
//            attributes.addFlashAttribute("message", "更新成功");
//        }
//        return "redirect:/admin/categorys";
//    }
}
