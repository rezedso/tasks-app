package com.ignacio.tasks.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private String status;
    private String priority;
    private Long boardId;
    private String boardName;
    private Long authorId;
    private String authorName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime updatedAt;
}
