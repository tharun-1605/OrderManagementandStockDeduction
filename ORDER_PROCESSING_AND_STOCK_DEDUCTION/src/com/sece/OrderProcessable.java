package com.sece;

public interface OrderProcessable {
    void placeOrder(Order order) throws OutOfStockException, InvalidOrderException;
    void updateStockLevel(int productId, int quantity) throws OutOfStockException;
    String generateOrderReceipt(Order order);
}

