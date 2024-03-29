package com.ignacio.tasks.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileUploadService {
    String uploadUserImageFile(MultipartFile multipartFile) throws IOException;
}
