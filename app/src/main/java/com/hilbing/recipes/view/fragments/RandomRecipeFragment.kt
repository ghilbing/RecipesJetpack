package com.hilbing.recipes.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hilbing.recipes.R
import com.hilbing.recipes.viewmodel.RandomRecipeViewModel

class RandomRecipeFragment : Fragment() {

    private lateinit var randomRecipeViewModel: RandomRecipeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        randomRecipeViewModel =
                ViewModelProvider(this).get(RandomRecipeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_random_recipe, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        randomRecipeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}