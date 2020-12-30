package com.aitao.myblog.controller;

import com.aitao.myblog.domain.Blog;
import com.aitao.myblog.domain.BlogLabel;
import com.aitao.myblog.service.IBlogLabelService;
import com.aitao.myblog.utils.ResponseCode;
import com.aitao.myblog.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Author : AiTao
 * Date : 2020/10/23
 * Time : 23:45
 * Information : 博客标签页控制器
 */
@Controller
@RequestMapping("/label")
public class BlogLabelController {
    @Autowired
    private IBlogLabelService blogLabelService;

    /**
     * 加载blog-label.html页
     *
     * @return
     */
    @RequestMapping({"", "/"})
    public String label(@RequestParam(value = "labelId", required = false) String labelId,
                        Model model) {
        List<BlogLabel> labels = blogLabelService.getLabelsByStatus((byte) 1);
        if (labelId != null) {
            model.addAttribute("labelId", labelId);
        }
        model.addAttribute("labels", labels);
        return "blog-label";
    }

    /**
     * 加载选中的标签下的所有博客数据
     *
     * @return
     */
    @GetMapping("/random/{id}")//label/random/3
    @ResponseBody
    public Map<String, Object> randomLabel(@PathVariable("id") String id) {
        Long no = StringUtils.strToLong(id);
        BlogLabel randomLabel = blogLabelService.getLabelById(no);
        System.out.println(randomLabel);
        return ResponseCode.ok(randomLabel.getBlogs());
    }
}
