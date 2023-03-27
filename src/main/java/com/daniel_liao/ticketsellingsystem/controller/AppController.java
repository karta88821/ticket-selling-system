package com.daniel_liao.ticketsellingsystem.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.daniel_liao.ticketsellingsystem.security.RoleRepository;
import com.daniel_liao.ticketsellingsystem.security.User;
import com.daniel_liao.ticketsellingsystem.security.UserRepository;

@Controller
public class AppController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("activeLink", "Home");
        return "home";
    }

    @GetMapping("/register")
    public String registerionPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping(value="/do-register")
    public String handleRegisterion(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        user.setRole(roleRepo.findByName("NORMAL"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return "register_success";
    }

    @GetMapping("/users/all")
    public String usersListPage(Model model) {
        List<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        model.addAttribute("activeLink", "Users");
        return "users";
    }

}
