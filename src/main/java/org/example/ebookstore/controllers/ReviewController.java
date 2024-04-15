package org.example.ebookstore.controllers;

import jakarta.validation.Valid;
import org.example.ebookstore.entities.dtos.ReviewResultDto;
import org.example.ebookstore.entities.dtos.ReviewSubmissionDto;
import org.example.ebookstore.services.interfaces.RatingService;
import org.example.ebookstore.services.interfaces.ReviewService;
import org.example.ebookstore.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;
    private final RatingService ratingService;

    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService, RatingService ratingService) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.ratingService = ratingService;
    }

    @PostMapping("/books/{bookId}/review")
    public ResponseEntity<?> reviewBook(@PathVariable("bookId") Long bookId,
                                        @Valid @RequestBody ReviewSubmissionDto reviewSubmissionDto,
                                        Authentication authentication, Model model) {
        Long userId = this.userService.getUserId(model, authentication);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You have to be logged in.");
        }
        if (!this.userService.hasUserPurchasedBook(userId, bookId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only review books that you have purchased.");
        }
        if (this.userService.hasUserReviewedBook(userId, bookId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("You have already reviewed this book.");
        }

        ReviewResultDto result = this.reviewService.createReview(reviewSubmissionDto, userId, bookId);
        return ResponseEntity.ok(result);
    }
}
