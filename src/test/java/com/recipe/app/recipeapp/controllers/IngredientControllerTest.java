package com.recipe.app.recipeapp.controllers;

import com.recipe.app.recipeapp.commands.IngredientCommand;
import com.recipe.app.recipeapp.commands.RecipeCommand;

import com.recipe.app.recipeapp.commands.UnitOfMeasureCommand;
import com.recipe.app.recipeapp.service.IngredientService;
import com.recipe.app.recipeapp.service.RecipeService;
import com.recipe.app.recipeapp.service.UnitOfMeasureService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

   @Mock
   IngredientService ingredientService;

   @Mock
   UnitOfMeasureService unitOfMeasureService;

    @InjectMocks
    IngredientController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getIngredientTest() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);
        when(recipeService.findRecipeCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/ingredients/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService,times(1)).findRecipeCommandById(anyLong());
    }

    @Test
    void getIngredientViewByIdTest() throws Exception {
        IngredientCommand command = new IngredientCommand();
        command.setId(1L);

        when(ingredientService.findRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/ingredients/1/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));

        verify(ingredientService, times(1)).findRecipeIdAndIngredientId(anyLong(), anyLong());

    }

    @SneakyThrows
    @Test
    void updateIngredientById(){
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = new HashSet<>();
        UnitOfMeasureCommand unitOfMeasureCommand1 = new UnitOfMeasureCommand();
        unitOfMeasureCommand1.setId(1L);

        UnitOfMeasureCommand unitOfMeasureCommand2 = new UnitOfMeasureCommand();
        unitOfMeasureCommand2.setId(2L);

        unitOfMeasureCommands.add(unitOfMeasureCommand1);
        unitOfMeasureCommands.add(unitOfMeasureCommand2);

        IngredientCommand command = new IngredientCommand();
        command.setId(1L);

        when(unitOfMeasureService.listAllUoms()).thenReturn(unitOfMeasureCommands);
        when(ingredientService.findRecipeIdAndIngredientId(anyLong(),anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/ingredients/1/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("uomList"))
                .andExpect(model().attributeExists("ingredient"));

    }
}