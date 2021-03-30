package com.hilbing.recipes.viewmodel

import androidx.lifecycle.*
import com.hilbing.recipes.model.database.RecipesRepository
import com.hilbing.recipes.model.entities.Recipes
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class RecipesViewModel(private val repository: RecipesRepository) : ViewModel() {
    fun insert(recipe: Recipes) = viewModelScope.launch {
        repository.insertRecipeData(recipe)
    }

    val allRecipesList: LiveData<List<Recipes>> = repository.allRecipesList.asLiveData()

}

class RecipeViewModelFactory(private val repository: RecipesRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RecipesViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return RecipesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}