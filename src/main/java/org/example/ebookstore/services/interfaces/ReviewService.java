package org.example.ebookstore.services.interfaces;

import org.example.ebookstore.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    Page<Review> getReviewsByBookId(Long bookId, Pageable pageable);
}
