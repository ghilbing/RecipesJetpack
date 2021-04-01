package com.hilbing.recipes.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hilbing.recipes.R
import com.hilbing.recipes.databinding.FragmentRandomRecipeBinding
import com.hilbing.recipes.viewmodel.RandomRecipeViewModel
import com.hilbing.recipes.viewmodel.RandomViewModel

class RandomRecipeFragment : Fragment() {
    
    private var mBinding: FragmentRandomRecipeBinding? = null

    private lateinit var mRandomViewModel: RandomViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentRandomRecipeBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRandomViewModel = ViewModelProvider(this).get(RandomViewModel::class.java)
        mRandomViewModel.getRandomRecipeFromAPI()
        randomViewModelObserver()
        
    }
    
    private fun randomViewModelObserver(){
        mRandomViewModel.randomRecipeResponse.observe(viewLifecycleOwner,
               Observer { randomResponse ->
                    randomResponse?.let {
                        Log.i("Random Recipe Response", "$randomResponse.randomrecipes[0]")
                    }
                })
        mRandomViewModel.randomRecipeLoadingError.observe(viewLifecycleOwner,
               Observer { dataError ->
                    dataError?.let {
                        Log.i("Random Recipe API Error", "$dataError")
                    }
                })
        mRandomViewModel.loadRandomRecipe.observe(viewLifecycleOwner,
                { loadRandomRecipe ->
                    loadRandomRecipe?.let {
                        Log.i("Random Dish Loading", "$loadRandomRecipe")
                    }
                })

    }



    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}