package ru.itmo.PreCrime.model;

public enum CrimeType {
    INTENTIONAL("Умышленное"),
    UNINTENTIONAL("Неумышленное");

    private final String displayText;


    CrimeType(String displayText) {
        this.displayText = displayText;
    }

    public String getDisplayText() {
        return displayText;
    }
}
