package com.sece;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InventoryManager implements OrderProcessable {
    private Connection conn;

    public InventoryManager() {
        try {
            // Replace with your actual database connection details
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ordermanagement", "root", "gb1605");
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
    }

    public boolean adminLogin(String username, String password) throws SQLException {
        String query = "SELECT * FROM Admin WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public void addProduct(Product product) throws SQLException {
        String insertSQL = "INSERT INTO Product (product_id, name, price, quantity_in_stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            stmt.setInt(1, product.getProductId());
            stmt.setString(2, product.getName());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getQuantityInStock());
            stmt.executeUpdate();
        }
    }

    public void updateProduct(int productId, String name, double price, int quantity) throws SQLException {
        String updateSQL = "UPDATE Product SET name = ?, price = ?, quantity_in_stock = ? WHERE product_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateSQL)) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, quantity);
            stmt.setInt(4, productId);
            stmt.executeUpdate();
        }
    }

    public void removeProduct(int productId) throws SQLException {
        String deleteSQL = "DELETE FROM Product WHERE product_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {
            stmt.setInt(1, productId);
            stmt.executeUpdate();
        }
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException, InvalidOrderException {
        try {
			try {
			    conn.setAutoCommit(false);

			    for (Product product : order.getProducts()) {
			        checkStockAvailability(product.getProductId(), product.getQuantityInStock());
			        updateStockLevel(product.getProductId(), product.getQuantityInStock());
			    }

			    String insertOrderSQL = "INSERT INTO Orders (customer_id, total_amount, order_date) VALUES (?, ?, ?)";
			    try (PreparedStatement stmt = conn.prepareStatement(insertOrderSQL)) {
			        stmt.setInt(1, order.getCustomerId());
			        stmt.setDouble(2, order.getTotalAmount());
			        stmt.setDate(3, java.sql.Date.valueOf(order.getOrderDate()));
			        stmt.executeUpdate();
			    }
			    conn.commit();
			} catch (SQLException e) {
			    conn.rollback();
			    throw new InvalidOrderException("Order processing failed: " + e.getMessage());
			}
		} catch (OutOfStockException e) {	
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InvalidOrderException e) {
			e.printStackTrace();
		}
    }

    private void checkStockAvailability(int productId, int requestedQuantity) throws OutOfStockException {
        String queryStockSQL = "SELECT quantity_in_stock FROM Product WHERE product_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(queryStockSQL)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int availableStock = rs.getInt("quantity_in_stock");
                if (availableStock < requestedQuantity) {
                    throw new OutOfStockException("Product ID " + productId + " is out of stock.");
                }
            } else {
                throw new OutOfStockException("Product ID " + productId + " does not exist.");
            }
        } catch (SQLException e) {
            throw new OutOfStockException("Failed to check stock availability: " + e.getMessage());
        }
    }

    @Override
    public void updateStockLevel(int productId, int quantity) throws OutOfStockException {
        String updateStockSQL = "UPDATE Product SET quantity_in_stock = quantity_in_stock - ? WHERE product_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateStockSQL)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new OutOfStockException("Failed to update stock level: " + e.getMessage());
        }
    }

    @Override
    public String generateOrderReceipt(Order order) {
        return "Order Receipt:\nOrder ID: " + order.getOrderId() + "\nCustomer ID: " + order.getCustomerId() + 
               "\nTotal Amount: $" + order.getTotalAmount() + "\nDate: " + order.getOrderDate();
    }

    public void saveCustomer(Customer customer) throws SQLException {
        if (conn == null) {
            throw new SQLException("Database connection not established.");
        }
        String sql = "INSERT INTO customer (name, contact_info) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getContactInfo());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customer.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating customer failed");
                }
            }
        }
    }
}
