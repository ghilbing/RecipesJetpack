package com.hilbing.recipes.application

import android.app.Application
import com.hilbing.recipes.model.database.RecipesRepository
import com.hilbing.recipes.model.database.RecipesRoomDatabase

class RecipeApplication : Application() {
    private val database by lazy {RecipesRoomDatabase.getDatabase(this@RecipeApplication)}
    val repository by lazy {RecipesRepository(database.recipeDao())}
}