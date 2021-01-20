package com.recipe.app.recipeapp.service;

import com.recipe.app.recipeapp.commands.IngredientCommand;
import com.recipe.app.recipeapp.converters.IngredientCommandToIngredient;
import com.recipe.app.recipeapp.converters.IngredientToIngredientCommand;
import com.recipe.app.recipeapp.domain.Ingredient;
import com.recipe.app.recipeapp.domain.Recipe;
import com.recipe.app.recipeapp.repositories.RecipeRepository;
import com.recipe.app.recipeapp.repositories.UnitOfMeasurRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
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
        Ingredient lastIngredient;

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
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
//                log.debug("ingredient description" + ingredient.getDescription());
//                log.debug("ingredient amout" + ingredient.getAmount());
//                log.debug("ingredient uom" + ingredient.getUom().getId());
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe saveRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> saveIngredient = saveRecipe.getIngredient().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(!saveIngredient.isPresent()){
                saveIngredient = saveRecipe.getIngredient().stream()
                        .filter(recipeIngredient -> recipeIngredient.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredient -> recipeIngredient.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredient -> recipeIngredient.getUom().getId().equals(ingredientCommand.getUom().getId()))
                        .findFirst();
            }

            return ingredientToIngredientCommand.convert(saveIngredient.get());

        } else {
            return new IngredientCommand();
        }


    }

    @Override
    public void deleteIngredientById(Long recipeId, Long ingredientId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if(optionalRecipe.isPresent()){
            Recipe recipe = optionalRecipe.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredient().stream()
                                                        .filter(ingredient -> ingredient.getId().equals(ingredientId))
                                                        .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredient = ingredientOptional.get();
                ingredient.setRecipe(null);
                recipe.getIngredient().remove(ingredient);
                recipeRepository.save(recipe);
            }
        } else {
            log.debug("recipe Id not found" + recipeId);
        }
    }


}
