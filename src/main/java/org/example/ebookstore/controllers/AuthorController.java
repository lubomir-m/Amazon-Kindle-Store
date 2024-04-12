package org.example.ebookstore.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.ebookstore.entities.Author;
import org.example.ebookstore.services.interfaces.BookService;
import org.example.ebookstore.services.interfaces.CategoryService;
import org.example.ebookstore.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthorController {
    private final BookService bookService;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public AuthorController(BookService bookService, UserService userService, CategoryService categoryService) {
        this.bookService = bookService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("/authors/{id}")
    public String viewAuthorPage(@PathVariable("id") Long id,
                                 @RequestParam(defaultValue = "0") int page,
                                 Model model, HttpServletRequest request) {
        Optional<Author> optional =


        return "author";
    }
}
