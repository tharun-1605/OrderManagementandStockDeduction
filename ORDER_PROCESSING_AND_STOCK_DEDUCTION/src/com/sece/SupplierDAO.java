package com.sece;

import java.sql.*;

public class SupplierDAO {
    private Connection conn;

    public SupplierDAO(Connection conn) {
        this.conn = conn;
    }

    // Add Supplier
    public void addSupplier(Supplier supplier) {
        String query = "INSERT INTO Supplier (name, contact_info, supplier_type) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getContactInfo());
            stmt.setString(3, supplier.getSupplierType());
            stmt.executeUpdate();
            System.out.println("Supplier added: " + supplier.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve Supplier by ID
    public Supplier getSupplier(int supplierId) {
        String query = "SELECT * FROM Supplier WHERE supplier_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, supplierId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Supplier(
                    rs.getInt("supplier_id"),
                    rs.getString("name"),
                    rs.getString("contact_info"),
                    rs.getString("supplier_type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
