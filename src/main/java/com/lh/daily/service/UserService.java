package com.lh.daily.service;

import com.lh.daily.po.User;

public interface UserService {
    /**
     * 获取用户名、密码
     * @param username
     * @param password
     * @return
     */
    User checkUser(String username, String password);
}
