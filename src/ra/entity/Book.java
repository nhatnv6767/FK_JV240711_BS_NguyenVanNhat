package ra.entity;

import ra.validation.Validator;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

public class Book implements IStoreManager {

    private int bookId;
    private String bookTitle;

    private String author;
    private Date publicationDate;
    private float price;
    private int categoryId;
    private String categoryName;

    public Book() {
    }

    public Book(int bookId, String bookTitle, String author, Date publicationDate, float price, int categoryId) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.author = author;
        this.publicationDate = publicationDate;
        this.price = price;
        this.categoryId = categoryId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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

    public String getBookInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("Title: %s, Author: %s, Publication Date: %s, Price: %.2f, Category: %s",
                getBookTitle(), getAuthor(), sdf.format(getPublicationDate()), getPrice(), getCategoryName());
    }

    @Override
    public void inputData(Scanner scanner, Validator validator) {
        setBookTitle(validator.getUniqueCategoryNameInput(scanner, "Enter book title: ", -1));
        setAuthor(validator.getNonEmptyStringInput(scanner, "Enter author: "));
        setPublicationDate(validator.getDateInput(scanner, "Enter publication date (dd/MM/yyyy): "));
        setPrice(validator.getPositiveFloatInput(scanner, "Enter price (>0): "));
        setCategoryId(validator.getValidCategoryId(scanner, "Enter category ID: "));
    }

    @Override
    public void displayData() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Book ID: " + getBookId());
        System.out.println("Book Title: " + getBookTitle());
        System.out.println("Author: " + getAuthor());
        System.out.println("Publication Date: " + sdf.format(getPublicationDate()));
        System.out.println("Price: " + getPrice());
        System.out.println("Category: " + getCategoryName());
    }
}
