package com.ignacio.tasks.service;

import com.ignacio.tasks.auth.dto.response.MessageDto;
import com.ignacio.tasks.dto.request.CommentRequestDto;
import com.ignacio.tasks.dto.request.UpdateCommentRequestDto;
import com.ignacio.tasks.dto.response.CommentResponseDto;

import java.util.List;

public interface ICommentService {
    CommentResponseDto createComment(Long taskId, CommentRequestDto commentRequestDto);
    MessageDto deleteComment(Long commentId);
    List<CommentResponseDto> getTaskComments(Long taskId);
    CommentResponseDto updateComment(Long commentId, UpdateCommentRequestDto updateCommentRequestDto);
}
