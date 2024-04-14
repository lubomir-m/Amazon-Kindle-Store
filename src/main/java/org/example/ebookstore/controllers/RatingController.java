package org.example.ebookstore.controllers;

import jakarta.validation.Valid;
import org.example.ebookstore.entities.dtos.RatingSubmissionDto;
import org.example.ebookstore.services.interfaces.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RatingController {
    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/books/{bookId}/rate")
    public ResponseEntity<?> rateBook(@PathVariable("bookId") Long bookId,
                                      @Valid @RequestBody RatingSubmissionDto ratingSubmissionDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = authentication.
    }
}
