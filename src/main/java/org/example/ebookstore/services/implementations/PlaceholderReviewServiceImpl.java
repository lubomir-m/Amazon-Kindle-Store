package org.example.ebookstore.services.implementations;

import org.example.ebookstore.entities.Review;
import org.example.ebookstore.repositories.ReviewRepository;
import org.example.ebookstore.services.interfaces.PlaceholderReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PlaceholderReviewServiceImpl implements PlaceholderReviewService {
    private final ReviewRepository reviewRepository;
    private List<Review> placeholderReviews = new ArrayList<>();

    @Autowired
    public PlaceholderReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
        this.placeholderReviews = this.reviewRepository.findFirst10ByOrderByIdAsc();
    }

    @Override
    public List<Review> getShuffledList() {
        List<Review> shuffledList = new ArrayList<>(this.placeholderReviews);
        Collections.shuffle(shuffledList);
        return shuffledList;
    }
}
