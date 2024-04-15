package org.example.ebookstore.services.interfaces;

import org.example.ebookstore.entities.dtos.RatingResultDto;

public interface RatingService {
    RatingResultDto createRating(int ratingValue, Long userId, Long bookId);
}
