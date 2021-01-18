package com.recipe.app.recipeapp.controllers;

import com.recipe.app.recipeapp.commands.IngredientCommand;
import com.recipe.app.recipeapp.service.IngredientService;
import com.recipe.app.recipeapp.service.RecipeService;
import com.recipe.app.recipeapp.service.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("/recipe/ingredients/{id}")
    public String getIngredient(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findRecipeCommandById(Long.valueOf(id)));
        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/ingredients/{recipeId}/{ingredientId}")
    public String getIngredientById(@PathVariable String recipeId,
                                    @PathVariable String ingredientId,
                                    Model model){
        model.addAttribute("ingredient",
                ingredientService.findRecipeIdAndIngredientId(Long.valueOf(recipeId),Long.valueOf(ingredientId)));

        return "recipe/ingredient/show";
    }

    @GetMapping
    @RequestMapping("/recipe/ingredients/{recipeId}/{ingredientId}/update")
    public String updateIngredientById(@PathVariable String recipeId,
                                       @PathVariable String ingredientId,
                                       Model model){

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        model.addAttribute("ingredient", ingredientService.findRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));

        return "recipe/ingredient/ingredientform";
    }

    @PostMapping
    @RequestMapping("/recipes/{recipeId}/ingredient")
    public String saveOrUpdateById(@ModelAttribute IngredientCommand command){

        IngredientCommand ingredientCommand = ingredientService.saveIngredientCommand(command);

        return "redirect:/recipe/ingredients/" + ingredientCommand.getRecipeId() + "/" + ingredientCommand.getId();
    }

}
