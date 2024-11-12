package ra.entity;

import ra.validation.Validator;

import java.util.Scanner;

public class Categories implements IStoreManager {

    private int categoryId;
    private String categoryName;
    private boolean categoryStatus;

    public Categories() {
    }

    public Categories(int categoryId, String categoryName, boolean categoryStatus) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryStatus = categoryStatus;
    }


    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isCategoryStatus() {
        return categoryStatus;
    }

    public void setCategoryStatus(boolean categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    @Override
    public void inputData(Scanner scanner, Validator validator) {
        setCategoryName(validator.getUniqueCategoryNameInput(scanner, "Enter category name: ", -1));
        setCategoryStatus(true);
    }

    @Override
    public void displayData() {
        System.out.println("Category ID: " + getCategoryId());
        System.out.println("Category name: " + getCategoryName());
        System.out.println("Category status: " + (isCategoryStatus() ? "Active" : "Inactive"));
    }
}
