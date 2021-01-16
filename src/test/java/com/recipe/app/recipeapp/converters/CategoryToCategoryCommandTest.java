package com.recipe.app.recipeapp.converters;

import com.recipe.app.recipeapp.commands.CategoryCommand;
import com.recipe.app.recipeapp.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

    private static final Long ID_VALUE = Long.valueOf(1L);
    private static final String DESCRIPTION = "Description";

    CategoryToCategoryCommand converter;


    @BeforeEach
    void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    void testNullObjects(){
        assertNull(converter.convert(null));
    }

    @Test
    void testEmplyObject(){
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    void convert() {
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommand command = converter.convert(category);

        //then
        assertEquals(ID_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
    }

    @Test
    void andThen() {
    }
}