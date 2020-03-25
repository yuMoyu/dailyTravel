package com.lh.daily.service;

import com.lh.daily.dao.UserRepository;
import com.lh.daily.po.User;
import com.lh.daily.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service //用于标注业务层组件
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    public User checkUser(String username, String password) {
        //MD5加密
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
