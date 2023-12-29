package com.ignacio.tasks.service.impl;

import com.ignacio.tasks.dto.request.CreateBoardRequestDto;
import com.ignacio.tasks.dto.request.UpdateListRequestDto;
import com.ignacio.tasks.dto.response.BoardResponseDto;
import com.ignacio.tasks.entity.Board;
import com.ignacio.tasks.entity.List;
import com.ignacio.tasks.entity.User;
import com.ignacio.tasks.enumeration.EAction;
import com.ignacio.tasks.enumeration.EEntityType;
import com.ignacio.tasks.exception.ResourceNotFoundException;
import com.ignacio.tasks.repository.BoardRepository;
import com.ignacio.tasks.repository.ListRepository;
import com.ignacio.tasks.service.IAuditLogService;
import com.ignacio.tasks.service.IBoardService;
import com.ignacio.tasks.service.IUtilService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements IBoardService {
    private final BoardRepository boardRepository;
    private final ListRepository listRepository;
    private final ModelMapper modelMapper;
    private final IUtilService utilService;
    private final IAuditLogService auditLogService;

    public BoardResponseDto createBoard(CreateBoardRequestDto createBoardRequestDto) {
        User user = utilService.getCurrentUser();
        Set<List> lists = new HashSet<>();
        java.util.List<List> newLists = new ArrayList<>();

        Board newBoard = Board.builder()
                .name(createBoardRequestDto.getName())
                .author(user)
                .build();

        if (createBoardRequestDto.getLists() != null && !createBoardRequestDto.getLists().isEmpty()) {
            for (String listName : createBoardRequestDto.getLists()) {
                Optional<List> existingList = listRepository.findByName(listName);
                if (existingList.isPresent()) {
                    lists.add(existingList.get());
                } else {
                    if (!listName.isEmpty()) {
                        List newList = List.builder()
                                .name(listName)
                                .build();
                        newLists.add(newList);
                    }
                }
            }
            if (!newLists.isEmpty()) {
                newLists = listRepository.saveAll(newLists);
                lists.addAll(newLists);
            }

            newBoard.setLists(lists);
        }
        boardRepository.save(newBoard);

        auditLogService.createAuditLog(newBoard.getId(), EEntityType.BOARD, EAction.CREATE, newBoard.getName());

        return modelMapper.map(newBoard, BoardResponseDto.class);
    }

    @Transactional
    public BoardResponseDto updateBoard(UpdateListRequestDto updateListRequestDto, Long boardId) {
        Board boardToUpdate = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Board was not found."));

        if (updateListRequestDto.getName() != null &&
                !boardToUpdate.getName().equals(updateListRequestDto.getName())) {
            boardToUpdate.setName(updateListRequestDto.getName());
        }

        auditLogService.createAuditLog(boardToUpdate.getId(), EEntityType.BOARD, EAction.UPDATE, boardToUpdate.getName());
        return modelMapper.map(boardToUpdate, BoardResponseDto.class);
    }

    public void deleteBoard(Long boardId) {
        Board board= boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Board not found."));
        boardRepository.deleteById(boardId);
        auditLogService.createAuditLog(boardId, EEntityType.BOARD, EAction.DELETE,board.getName());
    }
}
