package ra.presentation;

public enum CategoriesMenuEnum {
    LIST(1, "List all categories"),
    ADD(2, "Add new category"),
    UPDATE(3, "Update category"),
    DELETE(4, "Delete category"),
    STATISTIC(5, "Show statistics of products by category"),
    BACK(0, "Back to main menu");

    private final int value;
    private final String description;

    CategoriesMenuEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
