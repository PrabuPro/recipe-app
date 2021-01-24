package com.recipe.app.recipeapp.controllers;

import com.recipe.app.recipeapp.commands.RecipeCommand;
import com.recipe.app.recipeapp.domain.Recipe;
import com.recipe.app.recipeapp.exceptions.NotFoundException;
import com.recipe.app.recipeapp.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/recipe")
@Controller
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/{id}")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findById(Long.parseLong(id)));

        return "recipe/show";
    }

    @GetMapping
    @RequestMapping("/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeForm";
    }

    @GetMapping
    @RequestMapping("/update/{id}")
    public String updateRecipe(@PathVariable String id,Model model){
        model.addAttribute("recipe", recipeService.findRecipeCommandById(Long.valueOf(id)));

        return "recipe/recipeForm";
    }

    @PostMapping
    @RequestMapping(name = "recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command){
        RecipeCommand saveCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + saveCommand.getId();
    }

    @GetMapping
    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable String id){
        log.debug("id - "+ id );
        recipeService.deleteById(Long.valueOf(id));

        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){
        log.debug("Handling not found");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception",exception);
        return modelAndView;
    }
}
