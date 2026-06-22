package models.enums;

public enum SeedType {
    INV("Invalid"),
    MONOCOTS("Monocotyledon"),
    DICOTS("Dicotyledons");

    private final String label;

    // Constructor
    private SeedType(String label) {
        this.label = label;
    }

    // Method
    public String GetLabel() {
        return this.label;
    }
}
