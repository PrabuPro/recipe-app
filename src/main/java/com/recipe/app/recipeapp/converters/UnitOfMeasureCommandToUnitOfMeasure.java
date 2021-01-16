package com.recipe.app.recipeapp.converters;

import com.recipe.app.recipeapp.commands.UnitOfMeasureCommand;
import com.recipe.app.recipeapp.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if(source == null){
            return null;
        }

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(source.getId());
        unitOfMeasure.setDescription(source.getDescription());
        return unitOfMeasure;
    }

    @Override
    public <U> Converter<UnitOfMeasureCommand, U> andThen(Converter<? super UnitOfMeasure, ? extends U> after) {
        return null;
    }
}
