package com.recipe.app.recipeapp.service;

import com.recipe.app.recipeapp.domain.Recipe;
import com.recipe.app.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImagefile(Long id, MultipartFile multipartFile) throws IOException {
        Recipe recipe = recipeRepository.findById(id).get();

        Byte[] byteObject = new Byte[multipartFile.getBytes().length];
        int i = 0;
        for(byte b: multipartFile.getBytes()){
            byteObject[i++] = b;
        }
        recipe.setImage(byteObject);
        recipeRepository.save(recipe);
    }
}
