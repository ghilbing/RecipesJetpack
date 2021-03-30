package com.hilbing.recipes.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.hilbing.recipes.R
import com.hilbing.recipes.application.RecipeApplication
import com.hilbing.recipes.databinding.FragmentAllRecipesBinding
import com.hilbing.recipes.view.activities.AddUpdateDishActivity
import com.hilbing.recipes.view.adapters.RecipeAdapter
import com.hilbing.recipes.viewmodel.RecipeViewModelFactory
import com.hilbing.recipes.viewmodel.RecipesViewModel

class AllRecipesFragment : Fragment() {

    private lateinit var mBinding: FragmentAllRecipesBinding

    private val mRecipeViewModel: RecipesViewModel by viewModels {
        RecipeViewModelFactory((requireActivity().application as RecipeApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAllRecipesBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.rvRecipesList.layoutManager = GridLayoutManager(requireActivity(), 2)
        val recipeAdapter = RecipeAdapter(this@AllRecipesFragment)
        mBinding.rvRecipesList.adapter = recipeAdapter

        mRecipeViewModel.allRecipesList.observe(viewLifecycleOwner){
            recipes ->
            recipes.let{
                if(it.isNotEmpty()){
                    mBinding.rvRecipesList.visibility = View.VISIBLE
                    mBinding.tvNoDishesAddedYet.visibility = View.GONE
                    recipeAdapter.recipesList(it)
                } else {
                    mBinding.rvRecipesList.visibility = View.GONE
                    mBinding.tvNoDishesAddedYet.visibility = View.VISIBLE
                }
            }
        }
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_all_dishes, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_add_dish -> {
                startActivity(Intent(requireActivity(), AddUpdateDishActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}