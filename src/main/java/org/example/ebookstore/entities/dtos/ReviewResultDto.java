package org.example.ebookstore.entities.dtos;

public class ReviewResultDto {
    private double averageRating;
    private long ratingsCount;
    private ReviewDto reviewDto;

    public ReviewResultDto() {
    }

    public ReviewResultDto(double averageRating, long ratingsCount, ReviewDto reviewDto) {
        this.averageRating = averageRating;
        this.ratingsCount = ratingsCount;
        this.reviewDto = reviewDto;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public long getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(long ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public ReviewDto getReviewDto() {
        return reviewDto;
    }

    public void setReviewDto(ReviewDto reviewDto) {
        this.reviewDto = reviewDto;
    }
}
