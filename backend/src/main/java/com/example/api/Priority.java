package com.example.api;

public enum Priority {
    ALTA("Alta"),
    BAIXA("Baixa"),
    MEDIA("Média");
    private String description;
    Priority (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}