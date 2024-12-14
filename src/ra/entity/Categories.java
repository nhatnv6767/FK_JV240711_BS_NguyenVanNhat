package ra.entity;

import ra.validation.Validator;

import java.util.Scanner;

public class Categories implements IStoreManager {

    private int categoryId;
    private String categoryName;

    public Categories() {
    }

    public Categories(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
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

    @Override
    public void inputData(Scanner scanner, Validator validator) {
        setCategoryName(validator.getUniqueCategoryNameInput(scanner, "Enter category name:", -1));
    }

    @Override
    public void displayData() {
        System.out.println("Category ID: " + getCategoryId());
        System.out.println("Category Name: " + getCategoryName());
    }
}
