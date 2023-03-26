package com.daniel_liao.ticketsellingsystem.section;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sections")
public class SectionController {

    @Autowired
    private SectionService service;
    
    @GetMapping("/all")
    public String sectionsListPage(Model model) {
        List<Section> sections = service.getAllSections();
        model.addAttribute("sections", sections);
        model.addAttribute("activeLink", "Sections");
        Section section = new Section();
        model.addAttribute("section", section);
        return "sections";
    }

    @PostMapping("/all")
    public String handleAddEvents(@Valid Section section, BindingResult result, Model model) {

        if (result.hasErrors()) {

            List<String> errorStrings = new ArrayList<>();
            for (FieldError error : result.getFieldErrors()) {
                errorStrings.add(error.getDefaultMessage());
            }
            
            model.addAttribute("form_error", errorStrings);
            model.addAttribute("sections", service.getAllSections());
            return "sections";
        }

        service.addNewSection(section);

        return "redirect:/sections/all";
    }
}
