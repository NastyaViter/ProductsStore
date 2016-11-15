package com.netcracker.crm.entity.utils;

import com.netcracker.crm.entity.User;

/**
 * Created by egor on 06.11.2016.
 */
public class EntityBuilder {
    private EntityBuilder() {
    }

    public static User buildUser(int userId, String login, String password, String userName, String contactPhone, String contactAddress, String roleId) {
        User user = new User();
        user.setUserId(userId);
        user.setLogin(login);
        user.setPassword(password);
        user.setUserName(userName);
        user.setContactPhone(contactPhone);
        user.setContactAddress(contactAddress);
        user.setRoleId(roleId);
        return user;
    }

}