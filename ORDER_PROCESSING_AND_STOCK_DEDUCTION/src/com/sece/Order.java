package com.sece;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private int orderId;
    private int customerId;
    private List<Product> products;
    private double totalAmount;
    private LocalDate orderDate;

    public Order(int orderId, int customerId, List<Product> products, double totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.products = products;
        this.totalAmount = totalAmount;
        this.orderDate = LocalDate.now();
    }
    // Getters and setters

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
}
