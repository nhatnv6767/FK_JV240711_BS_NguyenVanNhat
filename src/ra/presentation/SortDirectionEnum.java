package ra.presentation;

public enum SortDirectionEnum {
    ASC(1, "Ascending"),
    DESC(2, "Descending");

    private final int value;
    private final String description;

    SortDirectionEnum(int value, String description) {
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
