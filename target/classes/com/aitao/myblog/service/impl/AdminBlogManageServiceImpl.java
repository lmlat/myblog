package com.aitao.myblog.service.impl;

import com.aitao.myblog.dao.IBlogRepository;
import com.aitao.myblog.domain.Blog;
import com.aitao.myblog.service.IAdminBlogManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/11/7
 * Time : 15:43
 * Information : 博客管理业务层接口实现类
 */
@Service
public class AdminBlogManageServiceImpl implements IAdminBlogManageService {
    @Autowired
    private IBlogRepository blogRepository;

    /**
     * 博客分页显示(5条/页)
     *
     * @return 返回分页的数据
     */
    @Override
    public Page<Blog> listBlogLimit(Blog blog, Pageable pageable) {
        /**root ：我们要查询的类型
         * query：添加查询条件
         * cb: 构建条件
         * specification为一个匿名内部类
         */
        //这里我们按照返回来的条件进行查询，就能得到我们想要的结果
        return blogRepository.findAll(new Specification<Blog>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Blog> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                //我理解为创建一个条件的集合
                List<Predicate> predicates = new ArrayList<>();
                //判断status是否为null
                if (blog.getStatus() != null && blog.getStatus() != -1) {
                    /**
                     * cb.equal()相当于判断后面两个参数是否一致
                     * root相当于我们的实体类的一个路径，使用get可以获取到我们的字段，因为我的status为Byte类型,所以是as(Byte.class)
                     * 如果为Int,就是as(Integer.class) 第二个参数为前台传过来的参数，这句话就相当于
                     * 数据库字段的值status = 前台传过来的值blog.getStatus()
                     */
                    predicates.add(cb.equal(root.get("status").as(Byte.class), blog.getStatus()));
                }
                if (blog.getStatus() == -1) {
                    predicates.add(cb.notEqual(root.get("status").as(Byte.class), (byte) 4));
                }
                //创建一个条件的集合，长度为上面满足条件的个数
                Predicate[] pre = new Predicate[predicates.size()];
                //根据修改时间降序排列
                query.orderBy(cb.desc(root.get("updateTime")));
                //将上面拼接好的条件返回
                return query.where(predicates.toArray(pre)).getRestriction();
            }
        }, pageable);
    }

    /**
     * 获取博客最新且按标题降序排序后的博客数据
     *
     * @return 返回Blog集合
     */
    @Override
    public List<Blog> listBlogLimit8() {
        return blogRepository.listBlogLimit8();
    }

    /**
     * 根据博客编号获取对应博客信息
     *
     * @param id 编号编号
     * @return 博客对象
     */
    @Override
    public Blog getBlogById(Long id) {
        return blogRepository.getOne(id);
    }

    /**
     * 统计博客总数
     *
     * @return 返回博客文章总数
     */
    @Override
    public long countBlog() {
        return blogRepository.count();
    }

    /**
     * 新建博客
     *
     * @param blog 博客信息
     * @return 返回博客对象
     */
    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    /**
     * 删除指定id的博客文章
     *
     * @param id 文章编号
     */
    @Override
    public void deleteById(Long id) {
        blogRepository.deleteById(id);
    }

    /**
     * 更新博客信息
     *
     * @param blog 博客信息
     * @return 返回记录数
     */
    @Override
    public Blog updateBlog(Blog blog) {
        Blog b = blogRepository.getOne(blog.getId());
        if (b == null) {
            return null;
        }
        blog.setCreateTime(b.getCreateTime());
        blog.setViews(b.getViews());
        blog.setBgImage(b.getBgImage());
        System.out.println("blog:" + blog);
        return blogRepository.save(blog);
    }

    /**
     * 博客文章加入回收站
     *
     * @param id 文章编号
     * @return 返回记录数
     */
    @Override
    public int updateBlogStatusById(Long id) {
        return blogRepository.updateBlogStatusById(id);
    }

}
