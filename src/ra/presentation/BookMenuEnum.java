package ra.presentation;

public enum BookMenuEnum {
    LIST(1, "List all books"),
    ADD(2, "Add new book"),
    VIEW_DETAIL(3, "View book details"),
    UPDATE(4, "Update book information"),
    DELETE(5, "Delete book"),
    LIST_BY_CATEGORY(6, "List books by category"),
    SORT(7, "Sort books"),
    ADVANCED_SEARCH(8, "Advanced search"),
    BACK(9, "Back to main menu");

    private final int value;
    private final String description;

    BookMenuEnum(int value, String description) {
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
