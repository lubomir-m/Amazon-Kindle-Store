package org.example.ebookstore.entities.dtos;

import org.example.ebookstore.entities.Currency;
import org.example.ebookstore.entities.Order;

import java.util.HashSet;
import java.util.Set;

public class UserDto {
    private String firstName;
    private String lastName;
    private Long wishlistId;
    private Long shoppingCartId;
    private int numberOfItemsInCart;
    private Set<Order> orders = new HashSet<>();
    private Currency selectedCurrency;

    public UserDto() {
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Currency getSelectedCurrency() {
        return selectedCurrency;
    }

    public void setSelectedCurrency(Currency selectedCurrency) {
        this.selectedCurrency = selectedCurrency;
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

    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public int getNumberOfItemsInCart() {
        return numberOfItemsInCart;
    }

    public void setNumberOfItemsInCart(int numberOfItemsInCart) {
        this.numberOfItemsInCart = numberOfItemsInCart;
    }
}
