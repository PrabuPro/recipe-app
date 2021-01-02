package com.recipe.app.recipeapp.repositories;

import com.recipe.app.recipeapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {


}
