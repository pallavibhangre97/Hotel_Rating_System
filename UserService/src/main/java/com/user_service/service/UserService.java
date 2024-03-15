package com.user_service.service;

import com.user_service.entity.User;

import java.util.List;

public interface UserService {

    public User createUser(User user);
    public User getUserById(int userId);
    public List<User> getAllUser();
    public void deleteUserById(int userId);
    public User updateUser(int userId,User user);
}
