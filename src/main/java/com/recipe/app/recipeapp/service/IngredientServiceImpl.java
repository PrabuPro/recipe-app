package com.recipe.app.recipeapp.service;

import com.recipe.app.recipeapp.commands.IngredientCommand;
import com.recipe.app.recipeapp.converters.IngredientToIngredientCommand;
import com.recipe.app.recipeapp.domain.Recipe;
import com.recipe.app.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);

        Optional<IngredientCommand> optionalIngredientCommand = null;
        
        if(recipe.isPresent()){
            optionalIngredientCommand = recipe.get().getIngredient().stream()
                                        .filter(ingredient -> ingredient.getId().equals(ingredientId))
                                        .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                                        .findFirst();
        }

        return optionalIngredientCommand.get();
    }
}
