package org.example.ebookstore.entities.dtos;

public class UserDto {
    private String firstName;
    private String lastName;
    private Long wishlistId;
    private Long shoppingCartId;
    private int numberOfItemsInCart;

    public UserDto() {
    }

    public UserDto(String firstName, String lastName, Long wishlistId, Long shoppingCartId, int numberOfItemsInCart) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.wishlistId = wishlistId;
        this.shoppingCartId = shoppingCartId;
        this.numberOfItemsInCart = numberOfItemsInCart;
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
