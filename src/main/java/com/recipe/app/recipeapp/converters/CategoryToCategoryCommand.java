package com.recipe.app.recipeapp.converters;

import com.recipe.app.recipeapp.commands.CategoryCommand;
import com.recipe.app.recipeapp.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category source) {
        if (source == null) {
            return null;
        }

        final CategoryCommand categoryCommand = new CategoryCommand();

        categoryCommand.setId(source.getId());
        categoryCommand.setDescription(source.getDescription());

        return categoryCommand;
    }

    @Override
    public <U> Converter<Category, U> andThen(Converter<? super CategoryCommand, ? extends U> after) {
        return null;
    }
}
