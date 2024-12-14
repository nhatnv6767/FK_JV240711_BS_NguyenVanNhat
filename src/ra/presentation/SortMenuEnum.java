package ra.presentation;

public enum SortMenuEnum {
    BY_TITLE(1, "Sort by title book"),
    BY_PRICE(2, "Sort by price"),
    BY_DATE(3, "Sort by publication date");

    private final int value;
    private final String description;

    SortMenuEnum(int value, String description) {
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
