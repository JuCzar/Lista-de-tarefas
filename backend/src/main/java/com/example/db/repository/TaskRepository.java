package com.example.db.repository;

import com.example.api.Status;
import com.example.db.entity.TaskEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
    List<TaskEntity> findAllByOrderByCreatedOnDesc();

    List<TaskEntity> findAllByStatusOrderByCreatedOnDesc(Status status);
}