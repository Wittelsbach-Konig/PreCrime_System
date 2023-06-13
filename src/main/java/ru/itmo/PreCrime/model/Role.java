package ru.itmo.PreCrime.model;

public enum Role {
    TECHNIC("Техник"),
    DETECTIVE("Детектив"),
    REACTIONGROUP("Группа реагирования"),
    AUDITOR("Аудитор");

    private final String displayText;


    Role(String displayText) {
        this.displayText = displayText;
    }

    public String getDisplayText() {
        return displayText;
    }

}
