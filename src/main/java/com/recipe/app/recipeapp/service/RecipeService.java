package com.recipe.app.recipeapp.service;

import com.recipe.app.recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

}
