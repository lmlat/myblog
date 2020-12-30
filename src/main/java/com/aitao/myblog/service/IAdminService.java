package com.aitao.myblog.service;

import com.aitao.myblog.domain.BlogLabel;
import com.aitao.myblog.domain.User;

import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/11/1
 * Time : 22:21
 * Information : 博客后台管理员业务层接口
 */
public interface IAdminService {
    /**
     * 根据用户名和密码查找指定用户
     *
     * @param username 登录的用户名
     * @param password 登录的用户密码
     * @return 返回存在的用户对象
     */
    User getUserByUserNameAndPassword(String username, String password);

    /**
     * 根据邮箱查找指定用户
     *
     * @param email 登录的邮箱
     * @return 返回存在的用户对象
     */
    User getUserByEmail(String email);
}
