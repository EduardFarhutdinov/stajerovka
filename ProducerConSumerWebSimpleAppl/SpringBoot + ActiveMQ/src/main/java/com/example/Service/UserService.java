package com.example.Service;

import com.example.jms.producer.JmsProducer;
import com.example.model.Role;
import com.example.model.User;
import com.example.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JmsProducer jmsProducer;


    @Override
    public UserDetails loadUserByUsername(String usernameFromLogin) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(usernameFromLogin);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String passwordFromForm = request.getParameter("password");
        String auth = usernameFromLogin.concat(passwordFromForm);
        System.out.println(usernameFromLogin);
        System.out.println(passwordFromForm);


        if (user == null) {
            throw new UsernameNotFoundException("пользователь не найден");
        } else {
            if (checkPassword(auth, user.getAuth())) {
                return user;
            } else {
                throw new BadCredentialsException("неверные данные");
            }
        }
    }

    public boolean checkPassword(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }


    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));

        user.setAuth(passwordEncoder.encode(user.getUsername().concat(user.getPassword())));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);
        jmsProducer.send(user);

        return true;
    }


    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);
    }

    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);

        }

        if (!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }

        userRepo.save(user);

    }


}