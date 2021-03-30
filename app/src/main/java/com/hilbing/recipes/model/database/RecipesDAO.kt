package com.hilbing.recipes.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hilbing.recipes.model.entities.Recipes
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDAO {

    @Insert
    suspend fun insertFavRecipeDetails(favRecipe: Recipes)

    @Query("SELECT * FROM RECIPES_TABLE ORDER BY ID")
    fun getAllRecipesList(): Flow<List<Recipes>>


}