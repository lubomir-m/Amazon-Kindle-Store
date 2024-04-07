package org.example.ebookstore.entities.dtos;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.example.ebookstore.entities.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class BookDto {
    private Long Id;
    private Set<AuthorDto> authors = new HashSet<>();
    private Set<Rating> ratings = new HashSet<>();
    private Double averageRating;
    private Long ratingsCount;
    private Set<Review> reviews = new HashSet<>();
    private String description;
    private String title;
    private LocalDate publicationDate;
    private PublisherDto publisher;

    private Long purchaseCount;
    private BigDecimal priceEur;
    private BigDecimal priceUsd;
    private BigDecimal priceAud;
    private BigDecimal priceBrl;
    private BigDecimal priceInr;
    private BigDecimal priceCny;
    private BigDecimal priceEgp;
    private BigDecimal priceNgn;
    private String imageUrl;
    private String coverColor;
    private String selectedCurrencyPrice;
    private Currency selectedCurrency;

    public BookDto() {
    }

    public Currency getSelectedCurrency() {
        return selectedCurrency;
    }

    public void setSelectedCurrency(Currency selectedCurrency) {
        this.selectedCurrency = selectedCurrency;
    }

    public String getSelectedCurrencyPrice() {
        return selectedCurrencyPrice;
    }

    public void setSelectedCurrencyPrice() {
        String symbol = this.selectedCurrency.getSymbol();
        BigDecimal price = null;
        String code = this.selectedCurrency.getCode();
        if (code.equals("EUR")) {
            price = this.priceEur;
        } else if (code.equals("USD")) {
            price = this.priceUsd;
        } else if (code.equals("AUD")) {
            price = this.priceAud;
        } else if (code.equals("BRL")) {
            price = this.priceBrl;
        } else if (code.equals("INR")) {
            price = this.priceInr;
        } else if (code.equals("CNY")) {
            price = this.priceCny;
        } else if (code.equals("EGP")) {
            price = this.priceEgp;
        } else if (code.equals("NGN")) {
            price = this.priceNgn;
        } else {
            price = this.priceEur;
        }

        this.selectedCurrencyPrice = String.format("%s%.2f", symbol, price);
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Set<AuthorDto> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorDto> authors) {
        this.authors = authors;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Long getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(Long ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public PublisherDto getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherDto publisher) {
        this.publisher = publisher;
    }

    public Long getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(Long purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public BigDecimal getPriceEur() {
        return priceEur;
    }

    public void setPriceEur(BigDecimal priceEur) {
        this.priceEur = priceEur;
    }

    public BigDecimal getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(BigDecimal priceUsd) {
        this.priceUsd = priceUsd;
    }

    public BigDecimal getPriceAud() {
        return priceAud;
    }

    public void setPriceAud(BigDecimal priceAud) {
        this.priceAud = priceAud;
    }

    public BigDecimal getPriceBrl() {
        return priceBrl;
    }

    public void setPriceBrl(BigDecimal priceBrl) {
        this.priceBrl = priceBrl;
    }

    public BigDecimal getPriceInr() {
        return priceInr;
    }

    public void setPriceInr(BigDecimal priceInr) {
        this.priceInr = priceInr;
    }

    public BigDecimal getPriceCny() {
        return priceCny;
    }

    public void setPriceCny(BigDecimal priceCny) {
        this.priceCny = priceCny;
    }

    public BigDecimal getPriceEgp() {
        return priceEgp;
    }

    public void setPriceEgp(BigDecimal priceEgp) {
        this.priceEgp = priceEgp;
    }

    public BigDecimal getPriceNgn() {
        return priceNgn;
    }

    public void setPriceNgn(BigDecimal priceNgn) {
        this.priceNgn = priceNgn;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCoverColor() {
        return coverColor;
    }

    public void setCoverColor(String coverColor) {
        this.coverColor = coverColor;
    }
}
