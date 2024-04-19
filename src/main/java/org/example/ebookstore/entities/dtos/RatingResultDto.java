package org.example.ebookstore.entities.dtos;

public class RatingResultDto {
    private String message;
    private double averageRating;
    private long ratingsCount;

    public RatingResultDto() {
    }

    public RatingResultDto(double averageRating, long ratingsCount) {
        this.averageRating = averageRating;
        this.ratingsCount = ratingsCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
}
