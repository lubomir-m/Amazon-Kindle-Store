package org.example.ebookstore.services.implementations;

import org.example.ebookstore.entities.Review;
import org.example.ebookstore.repositories.ReviewRepository;
import org.example.ebookstore.services.interfaces.PlaceholderReviewService;
import org.example.ebookstore.services.interfaces.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final PlaceholderReviewService placeholderReviewService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, PlaceholderReviewService placeholderReviewService) {
        this.reviewRepository = reviewRepository;
        this.placeholderReviewService = placeholderReviewService;
    }

    @Override
    public Page<Review> getReviewsByBookId(Long bookId, Pageable pageable) {
        return this.reviewRepository.findAllByBookId(bookId, pageable);
    }
}
