package com.sys.foodcommendbackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.foodcommendbackend.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LuoRuiJie
 * @since 2022-04-09
 */
public interface UserService extends IService<User> {

    boolean checkAccountUnique(String account);

    User saveWithHashPassword(User record);

    String createToken(User userInfo);

    User findUser(User userInfo);

    boolean checkPassword(User record, String password);


}
