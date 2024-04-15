package org.example.ebookstore.controllers;

import jakarta.validation.Valid;
import org.example.ebookstore.entities.dtos.RatingResultDto;
import org.example.ebookstore.entities.dtos.RatingSubmissionDto;
import org.example.ebookstore.services.interfaces.RatingService;
import org.example.ebookstore.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RatingController {
    private final RatingService ratingService;
    private final UserService userService;

    @Autowired
    public RatingController(RatingService ratingService, UserService userService) {
        this.ratingService = ratingService;
        this.userService = userService;
    }

    //TODO: check this, csrf
    @PostMapping("/books/{bookId}/rate")
    public ResponseEntity<?> rateBook(@PathVariable("bookId") Long bookId,
                                      @Valid @RequestBody RatingSubmissionDto ratingSubmissionDto,
                                      Authentication authentication, Model model) {
        Long userId = this.userService.getUserId(model, authentication);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You have to be logged in.");
        }
        if (!this.userService.hasUserPurchasedBook(userId, bookId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only rate books that you have purchased.");
        }
        if (this.userService.hasUserRatedBook(userId, bookId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("You have already rated this book.");
        }

        RatingResultDto result = this.ratingService.createRating(ratingSubmissionDto.getRating(),
                userId, bookId);
        return ResponseEntity.ok(result);
    }
}
