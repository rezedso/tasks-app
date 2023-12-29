package com.ignacio.tasks.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRequestDto {
    private String name;
    private String description;
    @Pattern(regexp = "^(TODO|IN_PROGRESS|COMPLETED)$")
    private String status = "TODO";
    @Pattern(regexp = "^(LOW|MEDIUM|HIGH)$")
    private String priority;
    private String endDate;
}

