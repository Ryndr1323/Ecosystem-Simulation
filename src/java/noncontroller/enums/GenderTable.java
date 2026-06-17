package noncontroller.enums;

public enum GenderTable {
    INV("Invalid"),
    MALE("Male"),
    FEMALE("Female");

    private final String label;

    // Constructor
    private GenderTable(String label) {
        this.label = label;
    }

    // Method
    public String GetLabel() {
        return this.label;
    }
}