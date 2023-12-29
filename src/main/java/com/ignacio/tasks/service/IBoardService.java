package com.ignacio.tasks.service;

import com.ignacio.tasks.dto.request.CreateBoardRequestDto;
import com.ignacio.tasks.dto.request.UpdateListRequestDto;
import com.ignacio.tasks.dto.response.BoardResponseDto;

public interface IBoardService {
    BoardResponseDto createBoard(CreateBoardRequestDto createBoardRequestDto);
    BoardResponseDto updateBoard(UpdateListRequestDto updateListRequestDto, Long boardId);
    void deleteBoard(Long boardId);
}
