package com.recipe.app.recipeapp.converters;

import com.recipe.app.recipeapp.commands.CategoryCommand;
import com.recipe.app.recipeapp.domain.Category;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.persistence.Convert;
import java.lang.annotation.Annotation;


@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand source) {
        if (source == null) {
            return null;
        }

        final Category category = new Category();
        category.setId(source.getId());
        category.setDescription(source.getDescription());
        return category;
    }

    @Override
    public <U> Converter<CategoryCommand, U> andThen(Converter<? super Category, ? extends U> after) {
        return null;
    }
}
