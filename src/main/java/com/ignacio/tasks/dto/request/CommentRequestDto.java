package com.ignacio.tasks.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {
    @NotEmpty(message = "Text must not be empty.")
    @Size(max = 255, message = "Size must be at most 255 characters long.")
    private String text;
    @Nullable
    @Positive
    private Long replyToId;
}
