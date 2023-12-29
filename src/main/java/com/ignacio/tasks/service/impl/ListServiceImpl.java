package com.ignacio.tasks.service.impl;

import com.ignacio.tasks.dto.request.CreateBoardRequestDto;
import com.ignacio.tasks.dto.request.UpdateListRequestDto;
import com.ignacio.tasks.dto.response.BoardResponseDto;
import com.ignacio.tasks.dto.response.ListResponseDto;
import com.ignacio.tasks.entity.Board;
import com.ignacio.tasks.entity.List;
import com.ignacio.tasks.entity.User;
import com.ignacio.tasks.exception.ResourceNotFoundException;
import com.ignacio.tasks.repository.BoardRepository;
import com.ignacio.tasks.repository.ListRepository;
import com.ignacio.tasks.service.IBoardService;
import com.ignacio.tasks.service.IListService;
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
public class ListServiceImpl implements IListService {
    private final ListRepository listRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ListResponseDto updateList(UpdateListRequestDto updateListRequestDto, Long listId) {
        List listToUpdate = listRepository.findById(listId)
                .orElseThrow(() -> new ResourceNotFoundException("List was not found."));

        if (updateListRequestDto.getName() != null &&
                !listToUpdate.getName().equals(updateListRequestDto.getName())) {
            listToUpdate.setName(updateListRequestDto.getName());
        }
        return modelMapper.map(listToUpdate, ListResponseDto.class);
    }
}
