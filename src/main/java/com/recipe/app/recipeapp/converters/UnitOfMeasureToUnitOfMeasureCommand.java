package com.recipe.app.recipeapp.converters;

import com.recipe.app.recipeapp.commands.UnitOfMeasureCommand;
import com.recipe.app.recipeapp.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure source) {
        if(source == null){
            return null;
        }

        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(source.getId());
        command.setDescription(source.getDescription());
        return command;
    }

    @Override
    public <U> Converter<UnitOfMeasure, U> andThen(Converter<? super UnitOfMeasureCommand, ? extends U> after) {
        return null;
    }
}
