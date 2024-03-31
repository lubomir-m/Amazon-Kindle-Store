package org.example.ebookstore.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
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
    private Long ratingsCount;
    @OneToMany(mappedBy = "book")
    private Set<Review> reviews = new HashSet<>();
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    @Size(min = 3)
    private String title;
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

    public void addAuthors(Author... authors) {
        Collections.addAll(this.authors, authors);
    }
    public void removeAuthors(Author... authors) {
        for (Author author : authors) {
            this.authors.remove(author);
        }
    }

    public void addCategories(Category... categories) {
        for (Category category : categories) {
            this.categories.add(category);
        }
    }
    public void removeCategories(Category... categories) {
        for (Category category : categories) {
            this.categories.remove(category);
        }
    }

    public void addRatings(Rating... ratings) {
        for (Rating rating : ratings) {
            this.ratings.add(rating);
        }
    }
    public void removeRatings(Rating... ratings) {
        for (Rating rating : ratings) {
            this.ratings.remove(rating);
        }
    }

    public void addReviews(Review... reviews) {
        for (Review review : reviews) {
            this.reviews.add(review);
        }
    }
    public void removeReviews(Review... reviews) {
        for (Review review : reviews) {
            this.reviews.remove(review);
        }
    }

    public void addOrders(Order... orders) {
        for (Order order : orders) {
            this.orders.add(order);
        }
    }
    public void removeOrders(Order... orders) {
        for (Order order : orders) {
            this.orders.remove(order);
        }
    }

    public void addWishlists(Wishlist... wishlists) {
        for (Wishlist wishlist : wishlists) {
            this.wishlists.add(wishlist);
        }
    }
    public void removeWishlists(Wishlist... wishlists) {
        for (Wishlist wishlist : wishlists) {
            this.wishlists.remove(wishlist);
        }
    }

    public void addShoppingCarts(ShoppingCart... shoppingCarts) {
        for (ShoppingCart shoppingCart : shoppingCarts) {
            this.shoppingCarts.add(shoppingCart);
        }
    }
    public void removeShoppingCarts(ShoppingCart... shoppingCarts) {
        for (ShoppingCart shoppingCart : shoppingCarts) {
            this.shoppingCarts.remove(shoppingCart);
        }
    }

    public Set<Author> getAuthors() {
        return Collections.unmodifiableSet(this.authors);
    }

    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(this.categories);
    }

    public Set<Rating> getRatings() {
        return Collections.unmodifiableSet(this.ratings);
    }

    public double getAverageRating() {
        return averageRating;
    }

    public long getRatingsCount() {
        return ratingsCount;
    }

    public Set<Review> getReviews() {
        return Collections.unmodifiableSet(this.reviews);
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public Set<Order> getOrders() {
        return Collections.unmodifiableSet(this.orders);
    }

    public Set<ShoppingCart> getShoppingCarts() {
        return Collections.unmodifiableSet(this.shoppingCarts);
    }

    public Set<Wishlist> getWishlists() {
        return Collections.unmodifiableSet(this.wishlists);
    }

    public long getPurchaseCount() {
        return purchaseCount;
    }

    public BigDecimal getPriceEur() {
        return priceEur;
    }

    public BigDecimal getPriceUsd() {
        return priceUsd;
    }

    public BigDecimal getPriceAud() {
        return priceAud;
    }

    public BigDecimal getPriceBrl() {
        return priceBrl;
    }

    public BigDecimal getPriceInr() {
        return priceInr;
    }

    public BigDecimal getPriceCny() {
        return priceCny;
    }

    public BigDecimal getPriceEgp() {
        return priceEgp;
    }

    public BigDecimal getPriceNgn() {
        return priceNgn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}