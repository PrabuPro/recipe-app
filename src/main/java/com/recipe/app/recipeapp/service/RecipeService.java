package com.recipe.app.recipeapp.service;

import com.recipe.app.recipeapp.commands.RecipeCommand;
import com.recipe.app.recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long l);

    RecipeCommand findRecipeCommandById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    void deleteById(Long id);

}
