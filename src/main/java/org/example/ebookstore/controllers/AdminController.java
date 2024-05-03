package org.example.ebookstore.controllers;

import org.example.ebookstore.entities.dtos.UserDto;
import org.example.ebookstore.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin-panel")
    public String displayAdminPanel(Model model) {
        List<UserDto> userDtos = this.userService.findAll();
        model.addAttribute("users", userDtos);

        return "admin-panel";
    }
}
