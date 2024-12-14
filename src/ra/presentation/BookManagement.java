package ra.presentation;

import ra.DAO.BookBusiness;
import ra.DAO.CategoriesBusiness;
import ra.entity.Book;
import ra.entity.Categories;
import ra.entity.CategoryStatistics;
import ra.util.UpdateOption;
import ra.validation.Validator;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;


public class BookManagement {

    private static final BookBusiness bookBusiness = new BookBusiness();
    private static CategoriesBusiness categoriesBusiness = new CategoriesBusiness();
    private static final Validator validator = new Validator();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("*************BOOK MANAGEMENT*************");
            System.out.println("1. Categories management");
            System.out.println("2. Books management");
            System.out.println("0. Exit");
            System.out.print("Please choose: ");
            choice = validator.getIntInput(scanner);
            switch (choice) {
                case 1:
                    categoriesMenu(scanner);
                    break;
                case 2:
                    booksMenu(scanner);
                    break;
                case 0:
                    System.err.println("Goodbye!");
                    break;
                default:
                    System.err.println("Invalid choice. Please choose again");
            }
        } while (choice != 0);
    }


    private static void booksMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("*************BOOKS MANAGEMENT*************");
            for (BookMenuEnum menu : BookMenuEnum.values()) {
                System.out.println(menu.getValue() + ". " + menu.getDescription());
            }
            System.out.print("Please choose: ");
            choice = validator.getIntInput(scanner);

            switch (choice) {
                case 1:
                    listBooks();
                    break;
                case 2:
                    addBook(scanner);
                    break;
                case 3:
                    viewBookDetail(scanner);
                    break;
                case 4:
                    System.out.println("Update book information");
                    break;
                case 5:
                    System.out.println("Delete book");
                    break;
                case 6:
                    System.out.println("List books by category");
                    break;
                case 7:
                    System.out.println("Sort books");
                    break;
                case 8:
                    System.out.println("Advanced search");
                    break;
                case 9:
                    break;
                default:
                    System.err.println("Invalid choice. Please choose again");
            }
        } while (choice != 9);
    }

    private static void listBooks() {
        Book[] books = bookBusiness.getAll();
        if (books.length == 0) {
            System.err.println("No books found");
            return;
        }
        System.out.println("\nBooks list:");
        for (Book book : books) {
            System.out.println(book.getBookInfo());
            ;
            System.out.println("====================================");
        }
    }

    private static void addBook(Scanner scanner) {
        Book book = new Book();
        book.inputData(scanner, validator);
        bookBusiness.insert(book);
    }

    private static void viewBookDetail(Scanner scanner) {
        System.out.print("Enter book id: ");
        int id = validator.getIntInput(scanner);

        Book book = bookBusiness.get(id);
        if (book == null) {
            System.err.println("Book not found");
            return;
        }

        System.out.println("Book information:");
        book.displayData();
    }


//    private static void addProduct(Scanner scanner) {
//        Products product = new Products();
//        product.inputData(scanner, validator);
//        productsBusiness.insert(product);
//    }
//
//    private static void displayAllProducts() {
//        Products[] products = productsBusiness.getAll();
//        System.out.println("Products list:");
//        for (Products product : products) {
//            product.displayData();
//            System.out.println("====================================");
//        }
//    }
//
//    private static void updateProduct(Scanner scanner) {
//        System.out.print("Enter product ID: ");
//        int productId = validator.getIntInput(scanner);
//        Products product = productsBusiness.get(productId);
//        if (product == null) {
//            System.err.println("Product not found");
//            return;
//        }
//
//        System.out.println("Product information:");
//        product.displayData();
//
//        Map<Integer, UpdateOption<Products>> updateOptions = new HashMap<>();
//        updateOptions.put(1, new UpdateOption<>("Update product name", (p, s) -> p.setProductName(validator.getUniqueProductNameInput(s, "Enter new product name: ", productId))));
//        updateOptions.put(2, new UpdateOption<>("Update stock", (p, s) -> p.setStock(validator.getPositiveIntInput(s, "Enter new stock: "))));
//        updateOptions.put(3, new UpdateOption<>("Update cost price", (p, s) -> p.setCostPrice(validator.getPositiveDoubleInput(s, "Enter new cost price: "))));
//        updateOptions.put(4, new UpdateOption<>("Update selling price", (p, s) -> p.setSellingPrice(validator.getPositiveDoubleInput(s, "Enter new selling price: "))));
//        updateOptions.put(5, new UpdateOption<>("Update category ID", (p, s) -> p.setCategoryId(validator.getValidCategoryId(s, "Enter new category ID: "))));
//
//        updateEntity(product, scanner, productsBusiness::update, updateOptions);
//
//    }
//
//    private static void deleteProduct(Scanner scanner) {
//        int productId = validator.getPositiveIntInput(scanner, "Enter product ID: ");
//        Products product = productsBusiness.get(productId);
//        if (product == null) {
//            System.err.println("Product not found");
//            return;
//        }
//        productsBusiness.delete(product);
//    }
//
//
//    private static void searchProductByPrice(Scanner scanner) {
//        double minPrice = validator.getPositiveDoubleInput(scanner, "Enter minimum price: ");
//        double maxPrice = validator.getPositiveDoubleInput(scanner, "Enter maximum price: ");
//        if (minPrice > maxPrice) {
//            System.err.println("Invalid price range");
//            return;
//        }
//        List<Products> products = productsBusiness.searchProductsByPriceRange(minPrice, maxPrice);
//        if (products.isEmpty()) {
//            System.err.println("Product not found");
//            return;
//        }
//        for (Products product : products) {
//            product.displayData();
//            System.out.println("====================================");
//        }
//    }
//
//    private static void displayProductsByCreatedAtDesc() {
//        List<Products> products = productsBusiness.getProductsByCreatedAtDesc();
//        if (products.isEmpty()) {
//            System.err.println("No products found");
//            return;
//        }
//        System.out.println("Products by created date (descending):");
//        for (Products product : products) {
//            product.displayData();
//            System.out.println("====================================");
//        }
//    }
//
//    private static void displayTop3ProfitableProducts() {
//        List<Products> products = productsBusiness.getTop3ProfitableProducts();
//        if (products.isEmpty()) {
//            System.err.println("No products found");
//            return;
//        }
//        System.out.println("Top 3 profitable products:");
//        // something ....
//        for (Products product : products) {
//            product.displayData();
//            System.out.println("Profit: " + (product.getSellingPrice() - product.getCostPrice()));
//            System.out.println("====================================");
//        }
//    }

//    CATEGORIES MENU

    private static void categoriesMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("*************CATEGORIES MANAGEMENT*************");
            for (CategoryMenuEnum menu : CategoryMenuEnum.values()) {
                System.out.println(menu.getValue() + ". " + menu.getDescription());
            }
            System.out.print("Please choose: ");
            choice = validator.getIntInput(scanner);

            switch (choice) {
                case 1:
                    displayAllCategories();
                    break;
                case 2:
                    addCategory(scanner);
                    break;
                case 3:
                    updateCategory(scanner);
                    break;
                case 4:
                    deleteCategory(scanner);
                    break;
                case 5:
                    searchCategories(scanner);
                    break;
                case 6:
                    break;
                default:
                    System.err.println("Invalid choice. Please choose again");
            }
        } while (choice != 6);
    }

    private static void displayAllCategories() {
        Categories[] categories = categoriesBusiness.getAll();

        if (categories.length == 0) {
            System.err.println("No categories found");
            return;
        }

        System.out.println("Category list:");
        for (Categories category : categories) {
            category.displayData();
            System.out.println("====================================");
        }
    }

    private static void addCategory(Scanner scanner) {

        System.out.print("Enter number of categories to add: ");
        int n = validator.getIntInput(scanner);
        for (int i = 0; i < n; i++) {
            System.out.println("Category " + (i + 1));
            Categories category = new Categories();
            category.setCategoryName(validator.getUniqueCategoryNameInput(scanner, "Enter category name: ", -1));
            categoriesBusiness.insert(category);
        }
    }


    private static void updateCategory(Scanner scanner) {
        System.out.print("Enter Category ID: ");
        int categoryId = validator.getIntInput(scanner);
        Categories category = categoriesBusiness.get(categoryId);
        if (category == null) {
            System.err.println("Category not found");
            return;
        }

        System.out.println("Category information:");
        category.displayData();

        Map<Integer, UpdateOption<Categories>> updateOptions = new HashMap<>();
        updateOptions.put(1, new UpdateOption<>("Update category name", (c, s) -> c.setCategoryName(validator.getUniqueCategoryNameInput(s, "Enter new category name: ", categoryId))));


        updateEntity(category, scanner, categoriesBusiness::update, updateOptions);
    }

    private static void deleteCategory(Scanner scanner) {
        int categoryId = validator.getPositiveIntInput(scanner, "Enter category ID: ");
        Categories category = categoriesBusiness.get(categoryId);
        if (category == null) {
            System.err.println("Category not found");
            return;
        }
        categoriesBusiness.delete(category);
    }

    private static void searchCategories(Scanner scanner) {
        System.out.print("Enter category name to search: ");
        String keyword = scanner.nextLine();

        List<Categories> categories = categoriesBusiness.searchCategories(keyword);
        if (categories.isEmpty()) {
            System.out.println("No categories found");
            return;
        }

        System.out.println("\nCategories found:");
        for (Categories category : categories) {
            category.displayData();
            System.out.println("====================================");
        }
    }


    private static <T> void updateEntity(T entity, Scanner scanner, Consumer<T> updateFunction, Map<Integer, UpdateOption<T>> updateOptions) {
        int choice;
        do {
            System.out.println("Choose field to update:");
            updateOptions.forEach((key, value) -> System.out.println(key + ". " + value.getDescription()));
            System.out.println("0. Cancel");
            System.out.print("Please choose: ");
            choice = validator.getIntInput(scanner);

            if (choice != 0 && updateOptions.containsKey(choice)) {
                updateOptions.get(choice).getAction().accept(entity, scanner);
                System.out.println("Field updated successfully");
            } else if (choice != 0) {
                System.err.println("Invalid choice. Please choose again");
            }
        } while (choice != 0);

        updateFunction.accept(entity);
    }


}
