package org.example.ebookstore.services.implementations;

import org.example.ebookstore.entities.Review;
import org.example.ebookstore.repositories.ReviewRepository;
import org.example.ebookstore.services.interfaces.PlaceholderReviewService;
import org.example.ebookstore.services.interfaces.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final PlaceholderReviewService placeholderReviewService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, PlaceholderReviewService placeholderReviewService) {
        this.reviewRepository = reviewRepository;
        this.placeholderReviewService = placeholderReviewService;
    }

    @Override
    public List<Review> getReviewsByBookId(Long bookId) {
        List<Review> reviews = this.reviewRepository.findAllByBookIdOrderBySubmissionDateDesc(bookId);
        reviews.addAll(this.placeholderReviewService.getShuffledList());
        return reviews;
    }
}
