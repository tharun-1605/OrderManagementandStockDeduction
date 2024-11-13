package com.sece;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final InventoryManager manager = new InventoryManager();
    private static final List<Supplier> suppliers = new ArrayList<>(); 

    public static void main(String[] args) {
        System.out.println("Are you an Admin or Customer? (Enter 'Admin' or 'Customer')");
        String userType = scanner.nextLine();

        if (userType.equalsIgnoreCase("Admin")) {
            adminLogin();
        } else {
            try {
                processCustomerOrder();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void adminLogin() {
        System.out.println("Enter Admin Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter Admin Password: ");
        String password = scanner.nextLine();

        try {
            if (manager.adminLogin(username, password)) {
                System.out.println("Admin login successful!");
                adminOperations();
            } else {
                System.out.println("Invalid credentials.");
            }
        } catch (SQLException e) {
            System.err.println("Login failed: " + e.getMessage());
        }
    }

    private static void adminOperations() {
        boolean running = true;
        while (running) {
            System.out.println("\nChoose an operation: ");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Remove Product");
            System.out.println("4. Logout");

            int choice = Integer.parseInt(scanner.nextLine());
            try {
                switch (choice) {
                    case 1 -> addProduct();
                    case 2 -> updateProduct();
                    case 3 -> removeProduct();
                    case 4-> addSupplier();
                    case 5 -> running = false;
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (SQLException e) {
                System.err.println("Operation failed: " + e.getMessage());
            }
        }
    }

    private static void addProduct() throws SQLException {
        System.out.println("Enter Product ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.println("Enter Product Price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.println("Enter Product Quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        Product product = new Product(id, name, price, quantity);
        manager.addProduct(product);
        System.out.println("Product added successfully.");
    }

    private static void updateProduct() throws SQLException {
        System.out.println("Enter Product ID to Update: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter New Product Name: ");
        String name = scanner.nextLine();
        System.out.println("Enter New Product Price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.println("Enter New Product Quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        manager.updateProduct(id, name, price, quantity);
        System.out.println("Product updated successfully.");
    }

    private static void removeProduct() throws SQLException {
        System.out.println("Enter Product ID to Remove: ");
        int id = Integer.parseInt(scanner.nextLine());
        manager.removeProduct(id);
        System.out.println("Product removed successfully.");
    }
    private static void addSupplier() {
        System.out.print("Enter Supplier Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Supplier Contact Info: ");
        String contactInfo = scanner.nextLine();
        System.out.print("Enter Supplier Type (Local/International): ");
        String type = scanner.nextLine();
        System.out.print("Enter Supplier Region/Country: ");
        String regionOrCountry = scanner.nextLine();
        String importLicense = "";

        if (type.equalsIgnoreCase("International")) {
            System.out.print("Enter Import License: ");
            importLicense = scanner.nextLine();
        }

        Supplier supplier;
        if (type.equalsIgnoreCase("Local")) {
            supplier = new LocalSupplier(suppliers.size() + 1, name, contactInfo, type, regionOrCountry);
        } else {
            supplier = new InternationalSupplier(suppliers.size() + 1, name, contactInfo, type, regionOrCountry, importLicense);
        }
        
        suppliers.add(supplier);
        System.out.println("Supplier added: " + supplier.getName());
    }

    private static void processCustomerOrder() throws SQLException {
        System.out.println("Enter Customer Name: ");
        String customerName = scanner.nextLine();
        System.out.println("Enter Customer Contact Info: ");
        String customerContact = scanner.nextLine();

        Customer customer = new Customer(customerName, customerContact);

        try {
            manager.saveCustomer(customer);
        } catch (SQLException e) {
            System.err.println("Failed to save customer: " + e.getMessage());
            return;
        }

        List<Product> products = new ArrayList<>();
        boolean addingProducts = true;

        while (addingProducts) {
            System.out.println("Enter Product ID: ");
            int productId = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter Product Name: ");
            String productName = scanner.nextLine();
            System.out.println("Enter Product Price: ");
            double productPrice = Double.parseDouble(scanner.nextLine());
            System.out.println("Enter Quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            Product product = new Product(productId, productName, productPrice, quantity);
            products.add(product);

            System.out.println("Add another product? (yes/no): ");
            String response = scanner.nextLine();
            addingProducts = response.equalsIgnoreCase("yes");
        }

        double totalAmount = products.stream().mapToDouble(p -> p.getPrice() * p.getQuantityInStock()).sum();

        Order order = new Order(1, customer.getId(), products, totalAmount);

        try {
            manager.placeOrder(order);
            System.out.println("Order placed successfully!");
            System.out.println(manager.generateOrderReceipt(order));
        } catch (OutOfStockException | InvalidOrderException e) {
            System.err.println(e.getMessage());
        }
    }
}
