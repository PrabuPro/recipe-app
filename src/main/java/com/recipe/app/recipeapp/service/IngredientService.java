package com.recipe.app.recipeapp.service;

import com.recipe.app.recipeapp.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteIngredientById(Long recipeId, Long ingredientId);
}
