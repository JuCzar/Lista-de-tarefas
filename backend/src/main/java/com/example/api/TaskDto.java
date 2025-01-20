package com.example.api;

import jakarta.validation.constraints.NotEmpty;

//import java.time.Instant;
import java.util.UUID;

public class TaskDto {

    private UUID id;
    @NotEmpty(message = "O título não deve ser nulo")
    private String title;
    private String createdOn;
    private String updatedOn;
    @NotEmpty(message = "A Data de Validade não pode ficar vazia, favor informar!")
    private String expireOn;
    private Priority priority;
    private Status status;
    @NotEmpty(message = "A descrição é obrigatória")
    private String description;
    private String statusClass;
    private String priorityClass;

    public String getStatusClass() {
        return statusClass;
    }

    public void setStatusClass(String statusClass) {
        this.statusClass = statusClass;
    }

    public String getPriorityClass() {
        return priorityClass;
    }

    public void setPriorityClass(String priorityClass) {
        this.priorityClass = priorityClass;
    }

    public TaskDto(){

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public @NotEmpty(message = "\r\n" + //
                "A Data de Validade não pode ficar vazia, favor informar!") String getExpireOn() {
        return expireOn;
    }

    public void setExpireOn(@NotEmpty(message = "\r\n" + //
                "A Data de Validade não pode ficar vazia, favor informar!") String expireOn) {
        this.expireOn = expireOn;
    }
}