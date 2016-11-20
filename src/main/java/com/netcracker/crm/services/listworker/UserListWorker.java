package com.netcracker.crm.services.listworker;

import com.netcracker.crm.entity.AbstractEntity;
import com.netcracker.crm.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by egor on 20.11.2016.
 */
@Component
public class UserListWorker{

    private UserListWorker (){

    }

    public static List<User> sortById(List<User> list){
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Integer.compare(o1.getId(),o2.getId());
            }
        });
        return list;
    }
    public static List<User> sortByUsername(List<User> list){
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getUserName().compareTo(o2.getUserName());
            }
        });
        return list;
    }

    public static List<User> sortByLogin(List<User> list){
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getLogin().compareTo(o2.getLogin());
            }
        });
        return list;
    }

    public static List<User> getFromTo(List<User> list, int from, int to){
        List<User> newList;
        if (list.size() < to){
            to = list.size();
        }
        newList = list.subList(from, to);
        return newList;
    }






}
