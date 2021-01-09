package com.recipe.app.recipeapp.controllers;


import com.recipe.app.recipeapp.domain.Category;
import com.recipe.app.recipeapp.domain.Recipe;
import com.recipe.app.recipeapp.domain.UnitOfMeasure;
import com.recipe.app.recipeapp.repositories.CategoryRepository;
import com.recipe.app.recipeapp.repositories.UnitOfMeasurRepository;
import com.recipe.app.recipeapp.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    public final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","/index"})
    public String getIndexPage(Model model){
        model.addAttribute("recipes",recipeService.getRecipes());
        return "index";
    }
}
