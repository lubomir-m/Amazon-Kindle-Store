package org.example.ebookstore.services.interfaces;

import org.example.ebookstore.entities.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviewsByBookId(Long bookId);
}
