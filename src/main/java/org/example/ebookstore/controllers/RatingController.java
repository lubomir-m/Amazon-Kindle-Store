package org.example.ebookstore.controllers;

import org.example.ebookstore.services.interfaces.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RatingController {
    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/books/{bookId}/rate")
    public ResponseEntity<?> rateBook(@PathVariable("bookId") Long bookId, )
}
