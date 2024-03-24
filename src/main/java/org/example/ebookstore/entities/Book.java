package org.example.ebookstore.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book extends BaseEntity {
    @ManyToMany
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "books_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();
    @OneToMany(mappedBy = "book")
    private Set<Rating> ratings = new HashSet<>();
    @Column(name = "average_rating", nullable = false)
    private double averageRating;
    @Column(name = "ratings_count", nullable = false)
    private long ratingsCount;
    @Column(name = "placeholder_review_count")
    @Min(0)
    @Max(10)
    private int placeholderReviewCount;
    @OneToMany(mappedBy = "book")
    private Set<Review> reviews = new HashSet<>();
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;
    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;
    @ManyToMany(mappedBy = "books")
    private Set<Order> orders = new HashSet<>();
    @ManyToMany(mappedBy = "books")
    private Set<ShoppingCart> shoppingCarts = new HashSet<>();
    @ManyToMany(mappedBy = "books")
    private Set<Wishlist> wishlists = new HashSet<>();
    @Column(name = "purchase_count", nullable = false)
    private long purchaseCount;
    @Column(name = "price_eur", precision = 19, scale = 4)
    private BigDecimal priceEur;
    @Column(name = "price_usd", precision = 19, scale = 4)
    private BigDecimal priceUsd;
    @Column(name = "price_aud", precision = 19, scale = 4)
    private BigDecimal priceAud;
    @Column(name = "price_brl", precision = 19, scale = 4)
    private BigDecimal priceBrl;
    @Column(name = "price_inr", precision = 19, scale = 4)
    private BigDecimal priceInr;
    @Column(name = "price_cny", precision = 19, scale = 4)
    private BigDecimal priceCny;
    @Column(name = "price_egp", precision = 19, scale = 4)
    private BigDecimal priceEgp;
    @Column(name = "price_ngn", precision = 19, scale = 4)
    private BigDecimal priceNgn;

    public Book() {
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
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

    public int getPlaceholderReviewCount() {
        return placeholderReviewCount;
    }

    public void setPlaceholderReviewCount(int placeholderReviewCount) {
        this.placeholderReviewCount = placeholderReviewCount;
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

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<ShoppingCart> getShoppingCarts() {
        return shoppingCarts;
    }

    public void setShoppingCarts(Set<ShoppingCart> shoppingCarts) {
        this.shoppingCarts = shoppingCarts;
    }

    public Set<Wishlist> getWishlists() {
        return wishlists;
    }

    public void setWishlists(Set<Wishlist> wishlists) {
        this.wishlists = wishlists;
    }

    public long getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(long purchaseCount) {
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
}