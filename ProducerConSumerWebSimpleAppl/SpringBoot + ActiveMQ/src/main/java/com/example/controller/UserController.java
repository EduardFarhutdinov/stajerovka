package com.example.controller;

import com.example.jms.consumer.JmsConsumer;
import com.example.jms.producer.JmsProducer;
import com.example.model.User;
import com.example.repositories.UserRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    @Autowired
    JmsProducer jmsProducer;
    @Autowired
    UserRepo userRepo;
    private static final Logger logger = Logger.getLogger(UserController.class);
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createUser(User user,HttpServletRequest request) {
        StringBuffer URL = request.getRequestURL();
        String Sertificat = request.getProtocol();
        System.out.println(Sertificat);
        System.out.println(URL);
        ModelAndView modelAndView = new ModelAndView();
        jmsProducer.send(user);
        userRepo.add(user);
        modelAndView.addObject("user",user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

//    @PostMapping("/api/user")
//    public User addUser(@RequestBody User user) {
//        jmsProducer.send(user);
//        return user;
//    }
//
//    @GetMapping(value = "/api/users")
//    public List<User> getUsers() {
//        List<User> users = userRepo.getAll();
//        return users;
//    }
}
