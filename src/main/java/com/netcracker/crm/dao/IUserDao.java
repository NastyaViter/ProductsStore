package com.netcracker.crm.dao;

import com.netcracker.crm.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by egor on 11.11.2016.
 */
public interface IUserDao extends IDao<User>{
    public List<User> getAllByRole(String roleId);
    public Map<Integer, String> login(String login);
}