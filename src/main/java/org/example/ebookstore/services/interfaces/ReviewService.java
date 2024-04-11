package org.example.ebookstore.services.interfaces;

import org.example.ebookstore.entities.Review;
import org.example.ebookstore.entities.dtos.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    Page<ReviewDto> getReviewsByBookId(Long bookId, Pageable pageable);
}
