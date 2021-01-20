package com.recipe.app.recipeapp.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    void saveImagefile(Long id, MultipartFile multipartFile) throws IOException;

}
