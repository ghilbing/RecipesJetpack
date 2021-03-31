package com.hilbing.recipes.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hilbing.recipes.R
import com.hilbing.recipes.application.RecipeApplication
import com.hilbing.recipes.viewmodel.FavoriteRecipesViewModel
import com.hilbing.recipes.viewmodel.RecipeViewModelFactory
import com.hilbing.recipes.viewmodel.RecipesViewModel

class FavoriteRecipesFragment : Fragment() {

    private lateinit var favoriteRecipesViewModel: FavoriteRecipesViewModel

    private val mRecipeViewModel: RecipesViewModel by viewModels {
        RecipeViewModelFactory((requireActivity().application as RecipeApplication).repository)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
       // mRecipeViewModel = ViewModelProvider(this).get(FavoriteRecipesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_favorite_recipes, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        favoriteRecipesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecipeViewModel.favoriteRecipesList.observe(viewLifecycleOwner){
            recipes ->
            recipes.let{
                if(it.isNotEmpty()){
                    for(recipe in it){
                        Log.i("Favorite Recipes", "${ recipe.id }:: ${recipe.title}")
                    }
                } else {
                    Log.i("Favorite Recipes", "List Empty")
                }
            }
        }
    }
}