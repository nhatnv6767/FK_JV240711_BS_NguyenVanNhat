package ra.presentation;

public enum CategoryMenuEnum {
    LIST(1, "List all categories"),
    ADD(2, "Add new category"),
    UPDATE(3, "Update category name"),
    DELETE(4, "Delete category"),
    SEARCH(5, "Search categories"),
    BACK(6, "Back to main menu");

    private final int value;
    private final String description;

    CategoryMenuEnum(int value, String description) {
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
