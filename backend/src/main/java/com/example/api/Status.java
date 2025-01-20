package com.example.api;

public enum Status {
    CONCLUIDO("Concluído"),
    NAO_INICIADO("Não iniciado"),
    EM_ANDAMENTO("Em andamento");
    private String description;
    Status (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}