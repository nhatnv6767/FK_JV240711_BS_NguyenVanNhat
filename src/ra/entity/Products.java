package ra.entity;

import ra.validation.Validator;

import java.time.LocalDate;
import java.util.Scanner;

public class Products implements IStoreManager {
    private int productId;
    private String productName;
    private int stock;
    private double costPrice;
    private double sellingPrice;
    private LocalDate createdAt;
    private int categoryId;

    public Products() {
    }

    public Products(int productId, String productName, int stock, double costPrice, double sellingPrice, LocalDate createdAt, int categoryId) {
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.createdAt = createdAt;
        this.categoryId = categoryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public void inputData(Scanner scanner, Validator validator) {

        
        setProductName(validator.getUniqueProductNameInput(scanner, "Enter product name: ", -1));
        setStock(validator.getPositiveIntInput(scanner, "Enter stock (>0): "));
        setCostPrice(validator.getPositiveDoubleInput(scanner, "Enter cost price (>0): "));
        setSellingPrice(validator.getPositiveDoubleInput(scanner, "Enter selling price (>0): "));
        setCategoryId(validator.getValidCategoryId(scanner, "Enter category ID: "));
        setCreatedAt(LocalDate.now());
    }

    @Override
    public void displayData() {
        System.out.println("Product ID: " + getProductId());
        System.out.println("Product name: " + getProductName());
        System.out.println("Stock: " + getStock());
        System.out.println("Cost price: " + getCostPrice());
        System.out.println("Selling price: " + getSellingPrice());
        System.out.println("Created at: " + getCreatedAt());
        System.out.println("Category ID: " + getCategoryId());
    }
}
