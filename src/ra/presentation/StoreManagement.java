package ra.presentation;

import ra.DAO.CategoriesBusiness;
import ra.DAO.ProductsBusiness;
import ra.entity.Categories;
import ra.entity.CategoryStatistics;
import ra.entity.Products;
import ra.util.UpdateOption;
import ra.validation.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;


public class StoreManagement {
    private static CategoriesBusiness categoriesBusiness = new CategoriesBusiness();
    private static ProductsBusiness productsBusiness = new ProductsBusiness();
    private static final Validator validator = new Validator();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("*************STORE MANAGEMENT*************");
            System.out.println("1. Products management");
            System.out.println("2. Categories management");
            System.out.println("0. Exit");
            System.out.print("Please choose: ");
            choice = validator.getIntInput(scanner);
            switch (choice) {
                case 1:
                    productsMenu(scanner);
                    break;
                case 2:
                    categoriesMenu(scanner);
                    break;
                case 0:
                    System.err.println("Goodbye!");
                    break;
                default:
                    System.err.println("Invalid choice. Please choose again");
            }
        } while (choice != 0);
    }


    private static void productsMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("*************PRODUCT MANAGEMENT*************");
            for (ProductMenuEnum menu : ProductMenuEnum.values()) {
                System.out.println(menu.getValue() + ". " + menu.getDescription());
            }
            System.out.print("Please choose: ");
            choice = validator.getIntInput(scanner);

            switch (choice) {
                case 1:
                    displayAllProducts();
                    break;
                case 2:
                    addProduct(scanner);
                    break;
                case 3:
                    updateProduct(scanner);
                    break;
                case 4:
                    deleteProduct(scanner);
                    break;
                case 5:
                    displayProductsByCreatedAtDesc();
                    break;
                case 6:
                    searchProductByPrice(scanner);
                    break;
                case 7:
                    displayTop3ProfitableProducts();
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Invalid choice. Please choose again");
            }
        } while (choice != 0);
    }

    private static void addProduct(Scanner scanner) {
        Products product = new Products();
        product.inputData(scanner, validator);
        productsBusiness.insert(product);
    }

    private static void displayAllProducts() {
        Products[] products = productsBusiness.getAll();
        System.out.println("Products list:");
        for (Products product : products) {
            product.displayData();
            System.out.println("====================================");
        }
    }

    private static void updateProduct(Scanner scanner) {
        System.out.print("Enter product ID: ");
        int productId = validator.getIntInput(scanner);
        Products product = productsBusiness.get(productId);
        if (product == null) {
            System.err.println("Product not found");
            return;
        }

        System.out.println("Product information:");
        product.displayData();

        Map<Integer, UpdateOption<Products>> updateOptions = new HashMap<>();
        updateOptions.put(1, new UpdateOption<>("Update product name", (p, s) -> p.setProductName(validator.getUniqueProductNameInput(s, "Enter new product name: ", productId))));
        updateOptions.put(2, new UpdateOption<>("Update stock", (p, s) -> p.setStock(validator.getPositiveIntInput(s, "Enter new stock: "))));
        updateOptions.put(3, new UpdateOption<>("Update cost price", (p, s) -> p.setCostPrice(validator.getPositiveDoubleInput(s, "Enter new cost price: "))));
        updateOptions.put(4, new UpdateOption<>("Update selling price", (p, s) -> p.setSellingPrice(validator.getPositiveDoubleInput(s, "Enter new selling price: "))));
        updateOptions.put(5, new UpdateOption<>("Update category ID", (p, s) -> p.setCategoryId(validator.getValidCategoryId(s, "Enter new category ID: "))));

        updateEntity(product, scanner, productsBusiness::update, updateOptions);

    }

    private static void deleteProduct(Scanner scanner) {
        int productId = validator.getPositiveIntInput(scanner, "Enter product ID: ");
        Products product = productsBusiness.get(productId);
        if (product == null) {
            System.err.println("Product not found");
            return;
        }
        productsBusiness.delete(product);
    }


    private static void searchProductByPrice(Scanner scanner) {
        double minPrice = validator.getPositiveDoubleInput(scanner, "Enter minimum price: ");
        double maxPrice = validator.getPositiveDoubleInput(scanner, "Enter maximum price: ");
        if (minPrice > maxPrice) {
            System.err.println("Invalid price range");
            return;
        }
        List<Products> products = productsBusiness.searchProductsByPriceRange(minPrice, maxPrice);
        if (products.isEmpty()) {
            System.err.println("Product not found");
            return;
        }
        for (Products product : products) {
            product.displayData();
            System.out.println("====================================");
        }
    }

    private static void displayProductsByCreatedAtDesc() {
        List<Products> products = productsBusiness.getProductsByCreatedAtDesc();
        if (products.isEmpty()) {
            System.err.println("No products found");
            return;
        }
        System.out.println("Products by created date (descending):");
        for (Products product : products) {
            product.displayData();
            System.out.println("====================================");
        }
    }

    private static void displayTop3ProfitableProducts() {
        List<Products> products = productsBusiness.getTop3ProfitableProducts();
        if (products.isEmpty()) {
            System.err.println("No products found");
            return;
        }
        System.out.println("Top 3 profitable products:");
        // something ....
        for (Products product : products) {
            product.displayData();
            System.out.println("Profit: " + (product.getSellingPrice() - product.getCostPrice()));
            System.out.println("====================================");
        }
    }

//    CATEGORIES MENU

    private static void categoriesMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("*************CATEGORIES MANAGEMENT*************");
            for (CategoriesMenuEnum menu : CategoriesMenuEnum.values()) {
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
                    displayProductsByCategory();
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Invalid choice. Please choose again");
            }
        } while (choice != 0);
    }

    private static void displayAllCategories() {
        Categories[] categories = categoriesBusiness.getAll();
        System.out.println("Category list:");
        for (Categories category : categories) {
            category.displayData();
            System.out.println("====================================");
        }
    }

    private static void addCategory(Scanner scanner) {
        Categories category = new Categories();
        category.inputData(scanner, validator);
        categoriesBusiness.insert(category);
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

    private static void displayProductsByCategory() {
        List<CategoryStatistics> statistics = categoriesBusiness.getProductsByCategory();

        if (statistics.isEmpty()) {
            System.err.println("No products found");
            return;
        }

        System.out.println("Products by category:");
        for (CategoryStatistics stat : statistics) {
            if (stat.getProductCount() > 0) {
                System.out.println("Category: " + stat.getCategoryName() + ", Number of products: " + stat.getProductCount());
            }
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
