package com.daniel_liao.ticketsellingsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.daniel_liao.ticketsellingsystem.entity.Section;
import com.daniel_liao.ticketsellingsystem.entity.User;
import com.daniel_liao.ticketsellingsystem.repository.RoleRepository;
import com.daniel_liao.ticketsellingsystem.repository.SectionRepository;
import com.daniel_liao.ticketsellingsystem.repository.UserRepository;

@Controller
public class AppController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private SectionRepository sectionRepo;


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
    public String handleRegisterion(User user) {
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

    @GetMapping("/sections/all")
    public String sectionsListPage(Model model) {
        List<Section> sections = sectionRepo.findAll();
        model.addAttribute("sections", sections);
        model.addAttribute("activeLink", "Sections");
        return "sections";
    }

    @GetMapping("/tickets/select-event")
    public String selectEventPage(Model model) {
        return "select-order";
    }

    @GetMapping("/tickets/select-section-and-seat")
    public String selectSectionAndSeatPage(Model model) {
        return "select-section-and-seat";
    }

    @GetMapping("/tickets/all")
    public String ticketsListPage(Model model) {
        return "tickets";
    }
}
