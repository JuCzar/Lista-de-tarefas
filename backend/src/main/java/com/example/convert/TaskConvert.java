package com.example.convert;

import com.example.api.Priority;
import com.example.api.Status;
import com.example.api.TaskDto;
import com.example.db.entity.TaskEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
//import java.util.Date;

@Component
public class TaskConvert {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public TaskEntity convertTaskDtoToTaskEntity(final TaskDto taskDto) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(taskDto.getTitle());
        taskEntity.setStatus(taskDto.getStatus());
        taskEntity.setPriority(taskDto.getPriority());
        taskEntity.setExpireOn(convertStringToInstant(taskDto.getExpireOn()));
        taskEntity.setDescription(taskDto.getDescription());
        return taskEntity;
    }

    public TaskDto convertTaskEntityToTaskDto(final TaskEntity taskEntity) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(taskEntity.getId());
        taskDto.setTitle(taskEntity.getTitle());
        taskDto.setPriority(taskEntity.getPriority());
        taskDto.setDescription(taskEntity.getDescription());
        taskDto.setStatus(taskEntity.getStatus());
        taskDto.setCreatedOn(convertInstantToString(taskEntity.getCreatedOn()));
        taskDto.setExpireOn(convertInstantToString(taskEntity.getExpireOn()));
        taskDto.setUpdatedOn(convertInstantToString(taskEntity.getUpdatedOn()));
        taskDto.setStatusClass(getStatusClass(taskEntity.getStatus()));
        taskDto.setPriorityClass(getPriorityClass(taskEntity.getPriority()));
        return taskDto;
    }

    // Método para converter Instant para String
    private String convertInstantToString(final Instant instant) {
        if (instant == null) {
            return null;
        }
        return DATE_FORMATTER.format(instant.atZone(java.time.ZoneId.systemDefault()).toLocalDate());
    }

    // Método para converter String para Instant
    public Instant convertStringToInstant(final String dateString) {
        try {
            LocalDate localDate = LocalDate.parse(dateString, DATE_FORMATTER);
            return localDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant();
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao converter a data: " + e.getMessage(), e);
        }
    }

    private String getStatusClass(Status status) {
        return switch (status) {
            case CONCLUIDO -> "badge badge-success";
            case EM_ANDAMENTO -> "badge badge-primary";
            case NAO_INICIADO -> "badge badge-secondary";
        };
    }

    private String getPriorityClass(Priority priority) {
        return switch (priority) {
            case ALTA -> "badge badge-danger";
            case MEDIA -> "badge badge-warning";
            case BAIXA -> "badge badge-primary";
        };
    }

    public Status convertStatus(String status) {
        switch (status) {
            case "Não iniciado":
                return Status.NAO_INICIADO;
            case "Em andamento":
                return Status.EM_ANDAMENTO;
            case "Concluído":
                return Status.CONCLUIDO;
            default:
                throw new IllegalArgumentException("Status inválido: " + status);
        }
    }
}
