package com.recipe.app.recipeapp.service;

import com.recipe.app.recipeapp.commands.IngredientCommand;
import com.recipe.app.recipeapp.converters.IngredientCommandToIngredient;
import com.recipe.app.recipeapp.converters.IngredientToIngredientCommand;
import com.recipe.app.recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.recipe.app.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.recipe.app.recipeapp.domain.Ingredient;
import com.recipe.app.recipeapp.domain.Recipe;
import com.recipe.app.recipeapp.domain.UnitOfMeasure;
import com.recipe.app.recipeapp.repositories.RecipeRepository;
import com.recipe.app.recipeapp.repositories.UnitOfMeasurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;
    UnitOfMeasurRepository unitOfMeasurRepository;



    @Mock
    RecipeRepository recipeRepository;

    @Mock
    IngredientService ingredientService;

    //init converters
    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        ingredientService = new IngredientServiceImpl(recipeRepository,  ingredientToIngredientCommand, ingredientCommandToIngredient, unitOfMeasurRepository);
    }

    @Test
    void findRecipeIdAndIngredientId() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(1L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //then
        IngredientCommand ingredientCommand = ingredientService.findRecipeIdAndIngredientId(1L, 3L);

        //when
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    void saveIngredientCommandTest(){
        IngredientCommand command = new IngredientCommand();
        command.setId(2L);
        command.setRecipeId(1L);

        Optional<Recipe> optionalRecipe = Optional.of(new Recipe());

        Recipe saveRecipe = new Recipe();
        saveRecipe.addIngredient(new Ingredient());
        saveRecipe.getIngredient().iterator().next().setId(2L);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);
        when(recipeRepository.save(any())).thenReturn(saveRecipe);

        IngredientCommand saveCommand = ingredientService.saveIngredientCommand(command);

        assertEquals(Long.valueOf(2L), saveCommand.getId());
    }

    @Test
    void deleteIngredientById(){
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setRecipe(recipe);
        recipe.addIngredient(ingredient);
        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        ingredientService.deleteIngredientById(1L, 1L);

        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(recipe);
    }
}