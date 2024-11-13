package com.sece;
public class Product {
    protected int productId;
    protected String name;
    protected double price;
    protected int quantityInStock;

    public Product(int productId, String name, double price, int quantityInStock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }
    // Getters and setters

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}
}

class ElectronicsProduct extends Product {
    public ElectronicsProduct(int productId, String name, double price, int quantityInStock) {
        super(productId, name, price, quantityInStock);
    }
}

class ClothingProduct extends Product {
    public ClothingProduct(int productId, String name, double price, int quantityInStock) {
        super(productId, name, price, quantityInStock);
    }
}
