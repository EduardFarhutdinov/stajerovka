package com.example.repositories;

import com.example.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserRepo {
   private List<User> users = new ArrayList<>();

    public void add(User user) {
        users.add(user);
    }

    public void clear() {
        users.clear();
    }

    public List<User> getAll(){
        return users;
    }
}
