package models.enums;

public enum AnimalType {
    INVALID("Invalid", "Tidak Valid"),
    HERBIVORE("Herbivora", "Pemakan Tumbuhan"),
    CARNIVORE("Karnivora", "Pemakan Daging");
    private final String label;
    private final String description;

    // Constructor
    AnimalType(String label, String description) {
        this.label = label;
        this.description = description;
    }

    // Methods
    public String getTypeLabel() {
        return this.label;
    }

    public String getTypeDescription() {
        return this.description;
    }
}
