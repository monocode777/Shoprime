package com.example.shoprime;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<CartItem> cartItems;
    private List<Order> orderHistory;

    private CartManager() {
        cartItems = new ArrayList<>();
        orderHistory = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addProduct(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        cartItems.add(new CartItem(product, 1));
    }

    public void removeProduct(Product product) {
        CartItem toRemove = null;
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                toRemove = item;
                break;
            }
        }
        if (toRemove != null) {
            cartItems.remove(toRemove);
        }
    }

    public void updateQuantity(String productId, int newQuantity) {
        if (newQuantity <= 0) {
            // Podríamos removerlo o ignorar
            return;
        }
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(newQuantity);
                break;
            }
        }
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void checkout() {
        if (cartItems.isEmpty()) return;
        
        Order order = new Order(
            String.valueOf(System.currentTimeMillis()),
            new ArrayList<>(cartItems),
            getTotal(),
            System.currentTimeMillis()
        );
        orderHistory.add(0, order);
        cartItems.clear();
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void clearCart() {
        cartItems.clear();
    }
}