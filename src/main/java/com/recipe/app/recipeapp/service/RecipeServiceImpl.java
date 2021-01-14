package com.recipe.app.recipeapp.service;

import com.recipe.app.recipeapp.domain.Recipe;
import com.recipe.app.recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("recipe service impl");
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long id) {

        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);

        if(!optionalRecipe.isPresent()){
            throw new RuntimeException("Recipe not found");
        }

        return optionalRecipe.get();
    }


}
