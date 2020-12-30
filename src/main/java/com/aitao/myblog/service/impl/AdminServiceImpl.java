package com.aitao.myblog.service.impl;

import com.aitao.myblog.dao.IAdminRepository;
import com.aitao.myblog.dao.IBlogLabelRepository;
import com.aitao.myblog.domain.BlogLabel;
import com.aitao.myblog.domain.User;
import com.aitao.myblog.service.IAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/11/1
 * Time : 22:20
 * Information : 博客后台管理员业务层接口实现类
 */
@Service
public class AdminServiceImpl implements IAdminService {
    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private IAdminRepository adminRepository;

    /**
     * 根据用户名和密码查找指定用户
     *
     * @param username 登录的用户名
     * @param password 登录的用户密码
     * @return 返回存在的用户对象
     */
    @Override
    public User getUserByUserNameAndPassword(String username, String password) {
        return adminRepository.getUserByUserNameAndPassword(username, password);
    }

    /**
     * 根据邮箱查找指定用户
     *
     * @param email 登录的邮箱
     * @return 返回存在的用户对象
     */
    @Override
    public User getUserByEmail(String email) {
        return adminRepository.getUserByEmail(email);
    }

}
