package com.recipe.app.recipeapp.controllers;

import com.recipe.app.recipeapp.service.IngredientService;
import com.recipe.app.recipeapp.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipe/ingredients")
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @RequestMapping("/{id}")
    public String getIngredient(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findRecipeCommandById(Long.valueOf(id)));
        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/{recipeId}/{ingredientId}")
    public String getIngredientById(@PathVariable String recipeId,
                                    @PathVariable String ingredientId,
                                    Model model){
        model.addAttribute("ingredient",
                ingredientService.findRecipeIdAndIngredientId(Long.valueOf(recipeId),Long.valueOf(ingredientId)));

        return "recipe/ingredient/show";
    }

}
