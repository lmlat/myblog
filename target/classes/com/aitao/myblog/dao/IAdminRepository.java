package com.aitao.myblog.dao;

import com.aitao.myblog.domain.BlogLabel;
import com.aitao.myblog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Author : AiTao
 * Date : 2020/11/1
 * Time : 22:18
 * Information : 管理员后台管理持久层接口
 */
public interface IAdminRepository extends JpaRepository<User,Long>,JpaSpecificationExecutor<User>{

    /**
     * 根据用户名、密码查找用户
     * :id为传递的参数
     * 根据参数的顺序，分别为?1，?2，?3......
     * @param username 用户名
     * @param password 密码
     * @return 返回存在的用户对象
     */
    @Query("select u from User as u where u.username=:username and u.password=:password")
    User getUserByUserNameAndPassword(@Param("username")String username, @Param("password")String password);

    /**
     * 根据用户邮箱查找用户
     * @param email 用户邮箱
     * @return 返回存在的用户对象
     */
    @Query("select u from User as u where u.email=:email")
    User getUserByEmail(@Param("email")String email);
}
