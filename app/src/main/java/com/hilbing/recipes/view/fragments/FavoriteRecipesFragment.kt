package com.hilbing.recipes.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hilbing.recipes.R
import com.hilbing.recipes.application.RecipeApplication
import com.hilbing.recipes.databinding.FragmentFavoriteRecipesBinding
import com.hilbing.recipes.model.entities.Recipes
import com.hilbing.recipes.view.activities.MainActivity
import com.hilbing.recipes.view.adapters.RecipeAdapter
import com.hilbing.recipes.viewmodel.FavoriteRecipesViewModel
import com.hilbing.recipes.viewmodel.RandomRecipeViewModel
import com.hilbing.recipes.viewmodel.RecipeViewModelFactory
import com.hilbing.recipes.viewmodel.RecipesViewModel

class FavoriteRecipesFragment : Fragment() {

    private lateinit var favoriteRecipesViewModel: FavoriteRecipesViewModel

    private var mBinding: FragmentFavoriteRecipesBinding? = null


    private val mRecipeViewModel: RecipesViewModel by viewModels {
        RecipeViewModelFactory((requireActivity().application as RecipeApplication).repository)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)

        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecipeViewModel.favoriteRecipesList.observe(viewLifecycleOwner){
            recipes ->
            recipes.let{
                mBinding!!.rvFavoriteRecipesList.layoutManager = GridLayoutManager(requireActivity(),2)
                val adapter = RecipeAdapter(this@FavoriteRecipesFragment)
                mBinding!!.rvFavoriteRecipesList.adapter = adapter
                if(it.isNotEmpty()){
                    mBinding!!.rvFavoriteRecipesList.visibility = View.VISIBLE
                    mBinding!!.tvNoFavoriteRecipesAvailable.visibility = View.GONE
                    adapter.recipesList(it)
                } else {
                    mBinding!!.rvFavoriteRecipesList.visibility = View.GONE
                    mBinding!!.tvNoFavoriteRecipesAvailable.visibility = View.VISIBLE
                }
            }
        }
    }

    fun recipeDetails(recipe: Recipes){
        findNavController().
        navigate(FavoriteRecipesFragmentDirections.actionNavigationFavoriteRecipesToRecipeDetailsFragment(recipe))
        if(requireActivity() is MainActivity){
            (activity as MainActivity?)?.hideBottomNavigationView()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

    override fun onResume() {
        super.onResume()
        if(requireActivity() is MainActivity){
            (activity as MainActivity?)?.showBottomNavigationView()
        }
    }
}