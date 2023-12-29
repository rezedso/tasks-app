package com.ignacio.tasks.service;

import com.ignacio.tasks.dto.request.UpdateListRequestDto;
import com.ignacio.tasks.dto.response.ListResponseDto;

public interface IListService {
    ListResponseDto updateList(UpdateListRequestDto updateListRequestDto, Long listId);
}
