package com.ignacio.tasks.config;

import com.ignacio.tasks.dto.response.BoardResponseDto;
import com.ignacio.tasks.dto.response.CommentResponseDto;
import com.ignacio.tasks.dto.response.TaskResponseDto;
import com.ignacio.tasks.entity.Board;
import com.ignacio.tasks.entity.Comment;
import com.ignacio.tasks.entity.List;
import com.ignacio.tasks.entity.Task;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<Board, BoardResponseDto> boardTypeMap = modelMapper
                .createTypeMap(Board.class, BoardResponseDto.class);
        boardTypeMap.addMappings(src -> src.using(new ListsToStringsConverter())
                        .map(Board::getLists, BoardResponseDto::setLists))
                .addMapping(src -> src.getAuthor().getId(), BoardResponseDto::setAuthorId)
                .addMapping(src -> src.getAuthor().getUsername(), BoardResponseDto::setAuthorName);

        TypeMap<Task, TaskResponseDto> taskTypeMap = modelMapper
                .createTypeMap(Task.class, TaskResponseDto.class);
        taskTypeMap.addMapping(src -> src.getBoard().getId(), TaskResponseDto::setBoardId);
        taskTypeMap.addMapping(src -> src.getBoard().getName(), TaskResponseDto::setBoardName);
        taskTypeMap.addMapping(src -> src.getAuthor().getId(), TaskResponseDto::setAuthorId);
        taskTypeMap.addMapping(src -> src.getAuthor().getUsername(), TaskResponseDto::setAuthorName);

        TypeMap<Comment,CommentResponseDto> commentTypeMap=modelMapper
                .createTypeMap(Comment.class, CommentResponseDto.class);
        commentTypeMap.addMapping(src -> src.getAuthor().getId(), CommentResponseDto::setAuthorId);
        commentTypeMap.addMapping(src -> src.getAuthor().getUsername(), CommentResponseDto::setAuthorName);
        commentTypeMap.addMapping(src -> src.getAuthor().getImageUrl(), CommentResponseDto::setAuthorImageUrl);
        commentTypeMap.addMapping(src -> src.getTask().getId(), CommentResponseDto::setTaskId);
        commentTypeMap.addMapping(src -> src.getReplyTo().getId(), CommentResponseDto::setReplyToId);

        return modelMapper;
    }

    public static class ListsToStringsConverter extends AbstractConverter<Set<List>, Set<String>> {
        protected Set<String> convert(Set<List> lists) {
            return lists.stream()
                    .map(List::getName).collect(Collectors.toSet());
        }
    }
}
