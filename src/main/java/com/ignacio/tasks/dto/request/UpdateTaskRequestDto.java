package com.ignacio.tasks.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskRequestDto implements Serializable {
    private String name;
    private String description;
    @Pattern(regexp = "^(TODO|IN_PROGRESS|COMPLETED)$")
    private String status;
    @Pattern(regexp = "^(LOW|MEDIUM|HIGH)$")
    private String priority;
    private String endDate;
}
