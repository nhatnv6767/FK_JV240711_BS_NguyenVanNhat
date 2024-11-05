package ra.entity;

public class CategoryStatistics {
    private String categoryName;
    private int productCount;

    public CategoryStatistics() {
    }

    public CategoryStatistics(String categoryName, int productCount) {
        this.categoryName = categoryName;
        this.productCount = productCount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
}
