package com.hilbing.recipes.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hilbing.recipes.model.entities.Recipes
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDAO {

    @Insert
    suspend fun insertFavRecipeDetails(favRecipe: Recipes)

    @Query("SELECT * FROM RECIPES_TABLE ORDER BY ID")
    fun getAllRecipesList(): Flow<List<Recipes>>

    @Update
    suspend fun updateRecipeDetails(recipe: Recipes)

    @Query("SELECT * FROM RECIPES_TABLE WHERE favorite_dish = 1")
    fun getFavoritesRecipesList() : Flow<List<Recipes>>



}