package com.hilbing.recipes.model.database

import androidx.annotation.WorkerThread
import com.hilbing.recipes.model.entities.Recipes
import kotlinx.coroutines.flow.Flow

class RecipesRepository(private val recipesDao: RecipesDAO) {
    @WorkerThread
    suspend fun insertRecipeData(recipe: Recipes){
        recipesDao.insertFavRecipeDetails(recipe)
    }

    val allRecipesList: Flow<List<Recipes>> = recipesDao.getAllRecipesList()

    @WorkerThread
    suspend fun updateRecipeData(recipe: Recipes){
        recipesDao.updateRecipeDetails(recipe)
    }

    val favoritesRecipes : Flow<List<Recipes>> = recipesDao.getFavoritesRecipesList()

    @WorkerThread
    suspend fun deleteRecipe(recipe: Recipes){
        recipesDao.deleteRecipe(recipe)
    }

    fun filteredRecipesList(value: String): Flow<List<Recipes>> = recipesDao.getFilteredRecipesList(value)
}