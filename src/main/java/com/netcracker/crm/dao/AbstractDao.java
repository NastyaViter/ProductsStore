package com.netcracker.crm.dao;

import com.netcracker.crm.entity.Entity;

import javax.sql.DataSource;

/**
 * Created by egor on 03.11.2016.
 */
public abstract class AbstractDao<T extends Entity> implements IDao<T>{
    protected DataSource dataSource;
}