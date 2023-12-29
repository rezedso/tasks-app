package com.ignacio.tasks.service.impl;

import com.ignacio.tasks.auth.dto.response.MessageDto;
import com.ignacio.tasks.dto.request.CommentRequestDto;
import com.ignacio.tasks.dto.request.UpdateCommentRequestDto;
import com.ignacio.tasks.dto.response.CommentResponseDto;
import com.ignacio.tasks.entity.Comment;
import com.ignacio.tasks.entity.Task;
import com.ignacio.tasks.entity.User;
import com.ignacio.tasks.enumeration.EAction;
import com.ignacio.tasks.enumeration.EEntityType;
import com.ignacio.tasks.exception.ResourceNotFoundException;
import com.ignacio.tasks.repository.TaskRepository;
import com.ignacio.tasks.repository.CommentRepository;
import com.ignacio.tasks.service.IAuditLogService;
import com.ignacio.tasks.service.ICommentService;
import com.ignacio.tasks.service.IUtilService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {
    private final IUtilService utilService;
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final IAuditLogService auditLogService;

    public List<CommentResponseDto> getTaskComments(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found."));
        Sort s = Sort.by("id").descending();

        List<Comment> comments = commentRepository.findAllByTask(task);

        return comments.stream().map(comment -> modelMapper.map(comment, CommentResponseDto.class)).toList();

    }

    public CommentResponseDto createComment(Long taskId, CommentRequestDto commentRequestDto) {
        User user = utilService.getCurrentUser();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found."));

        Comment comment = Comment.builder()
                .author(user)
                .task(task)
                .text(commentRequestDto.getText())
                .build();

        if (commentRequestDto.getReplyToId() != null) {
            Comment replyTo = commentRepository.findById(commentRequestDto.getReplyToId())
                    .orElseThrow(() -> new ResourceNotFoundException("Comment not found."));
            comment.setReplyTo(replyTo);
        }
        commentRepository.save(comment);

        auditLogService.createAuditLog(comment.getId(), EEntityType.COMMENT, EAction.CREATE);
        return modelMapper.map(comment, CommentResponseDto.class);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, UpdateCommentRequestDto updateCommentRequestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found."));

        if (!Objects.equals(comment.getText(), updateCommentRequestDto.getText()) &&
                !updateCommentRequestDto.getText().isEmpty()
        ) {
            comment.setText(updateCommentRequestDto.getText());
        }
        auditLogService.createAuditLog(comment.getId(), EEntityType.COMMENT, EAction.UPDATE);
        return modelMapper.map(comment, CommentResponseDto.class);
    }

    public MessageDto deleteComment(Long commentId) {
        commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found."));
        commentRepository.deleteById(commentId);
        auditLogService.createAuditLog(commentId, EEntityType.COMMENT, EAction.DELETE);

        return new MessageDto("Comment deleted");
    }
}
