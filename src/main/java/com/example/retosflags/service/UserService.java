package com.example.retosflags.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.retosflags.model.User;

@Service
public class UserService {

    private List<User> users;

    public UserService(){
        users=new ArrayList<>();
        User user1=new User("admin","admin");
        user1.setId(1L);
        users.add(user1);
    }

    public void addUser(User user) {
        users.add(user);
        System.out.println(users);
    }

    public User logUser(User user) {
        User loggedUser=new User();
        for (User u:users){
            if(u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())){
                loggedUser.setPassword(u.getPassword());
                loggedUser.setUsername(u.getUsername());
            }
        }
        if(loggedUser.getUsername()!=null && loggedUser.getPassword()!=null){
            return loggedUser;
        }
        return null;
    }

    
}
