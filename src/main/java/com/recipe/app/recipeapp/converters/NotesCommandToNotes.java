package com.recipe.app.recipeapp.converters;


import com.recipe.app.recipeapp.commands.NotesCommand;
import com.recipe.app.recipeapp.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand source) {
        if(source == null) {
            return null;
        }

        final Notes notes = new Notes();
        notes.setId(source.getId());
        notes.setRecipeNotes(source.getRecipeNotes());
        return notes;
    }

    @Override
    public <U> Converter<NotesCommand, U> andThen(Converter<? super Notes, ? extends U> after) {
        return null;
    }
}
