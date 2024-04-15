package org.example.ebookstore.services.implementations;

import org.example.ebookstore.entities.dtos.RatingResultDto;
import org.example.ebookstore.services.interfaces.RatingService;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {
    @Override
    public RatingResultDto createRating(int ratingValue, Long userId, Long bookId) {
        return null;
    }
}
