package com.recipe.app.recipeapp.service;

import com.recipe.app.recipeapp.commands.IngredientCommand;
import com.recipe.app.recipeapp.converters.IngredientCommandToIngredient;
import com.recipe.app.recipeapp.converters.IngredientToIngredientCommand;
import com.recipe.app.recipeapp.domain.Ingredient;
import com.recipe.app.recipeapp.domain.Recipe;
import com.recipe.app.recipeapp.repositories.RecipeRepository;
import com.recipe.app.recipeapp.repositories.UnitOfMeasurRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasurRepository unitOfMeasurRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 UnitOfMeasurRepository unitOfMeasurRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasurRepository = unitOfMeasurRepository;
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

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if(recipeOptional.isPresent()){

            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredient()
                                                            .stream()
                                                            .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                                                            .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredient = ingredientOptional.get();
                ingredient.setAmount(ingredientCommand.getAmount());
                ingredient.setDescription(ingredientCommand.getDescription());
                ingredient.setUom(unitOfMeasurRepository.findById(ingredientCommand.getUom().getId())
                            .orElseThrow(() -> new RuntimeException("UOM no found")));
            } else {
                recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
            }

            Recipe saveRecipe = recipeRepository.save(recipe);

            return ingredientToIngredientCommand.convert(saveRecipe.getIngredient().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst()
                    .get());

        } else {
            return new IngredientCommand();
        }


    }
}
