package com.example.db.entity;

import java.time.Instant;
import java.util.UUID;

import com.example.api.Priority;
import com.example.api.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_task", schema = "schemaTask")
public class TaskEntity {

    @Id
    //@GeneratedValue(generator = "uuid2")
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;
    @Column(name="title")
    private String title;
    @Column(name="created_on")
    private Instant createdOn;
    @Column(name="updated_on")
    private Instant updatedOn;
    @Column(name="expired_on")
    private Instant expireOn;
    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "description", columnDefinition = "TEXT", length=300)
    private String description;

    public TaskEntity(){

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

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Instant getExpireOn() {
        return expireOn;
    }

    public void setExpireOn(Instant expireOn) {
        this.expireOn = expireOn;
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

    @PrePersist
    protected void onCreate(){
        this.createdOn = Instant.now();
        this.updatedOn = Instant.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedOn = Instant.now();
    }
}