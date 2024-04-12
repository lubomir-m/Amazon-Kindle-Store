package org.example.ebookstore.entities.dtos;

import org.example.ebookstore.entities.Currency;
import org.example.ebookstore.entities.Order;
import org.example.ebookstore.entities.ShoppingCart;
import org.example.ebookstore.entities.Wishlist;

import java.util.HashSet;
import java.util.Set;

public class UserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private WishlistDto wishlist;
    private ShoppingCartDto shoppingCart;
    private Set<OrderDto> orders = new HashSet<>();
    private Currency selectedCurrency;
    private String pictureBase64;

    public UserDto() {
    }

    public String getPictureBase64() {
        return pictureBase64;
    }

    public void setPictureBase64(String pictureBase64) {
        this.pictureBase64 = pictureBase64;
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public WishlistDto getWishlist() {
        return wishlist;
    }

    public void setWishlist(WishlistDto wishlist) {
        this.wishlist = wishlist;
    }

    public ShoppingCartDto getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCartDto shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Set<OrderDto> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderDto> orders) {
        this.orders = orders;
    }

    public Currency getSelectedCurrency() {
        return selectedCurrency;
    }

    public void setSelectedCurrency(Currency selectedCurrency) {
        this.selectedCurrency = selectedCurrency;
    }
}
