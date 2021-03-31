package com.hilbing.recipes.view.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hilbing.recipes.R
import com.hilbing.recipes.application.RecipeApplication
import com.hilbing.recipes.databinding.DialogCustomListBinding
import com.hilbing.recipes.databinding.FragmentAllRecipesBinding
import com.hilbing.recipes.model.entities.Recipes
import com.hilbing.recipes.utils.Constants
import com.hilbing.recipes.view.activities.AddUpdateDishActivity
import com.hilbing.recipes.view.activities.MainActivity
import com.hilbing.recipes.view.adapters.CustomListItemAdapter
import com.hilbing.recipes.view.adapters.RecipeAdapter
import com.hilbing.recipes.viewmodel.RecipeViewModelFactory
import com.hilbing.recipes.viewmodel.RecipesViewModel

class AllRecipesFragment : Fragment() {

    private lateinit var mBinding: FragmentAllRecipesBinding

    private lateinit var mRecipeAdapter: RecipeAdapter
    private lateinit var mCustomListDialog: Dialog

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
        mRecipeAdapter = RecipeAdapter(this@AllRecipesFragment)

        mBinding.rvRecipesList.adapter = mRecipeAdapter

        mRecipeViewModel.allRecipesList.observe(viewLifecycleOwner){
            recipes ->
            recipes.let{
                if(it.isNotEmpty()){
                    mBinding.rvRecipesList.visibility = View.VISIBLE
                    mBinding.tvNoDishesAddedYet.visibility = View.GONE
                    mRecipeAdapter.recipesList(it)
                } else {
                    mBinding.rvRecipesList.visibility = View.GONE
                    mBinding.tvNoDishesAddedYet.visibility = View.VISIBLE
                }
            }
        }
    }

    fun recipeDetails(recipe: Recipes){
        findNavController().
        navigate(AllRecipesFragmentDirections.actionNavigationAllRecipesToRecipeDetailsFragment(recipe))
        if(requireActivity() is MainActivity){
            (activity as MainActivity?)?.hideBottomNavigationView()
        }
    }

    fun deleteRecipe(recipe: Recipes){
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(resources.getString(R.string.delete_recipe))
        builder.setMessage(resources.getString(R.string.are_you_sure_you_want_to_delete_this_recipe, recipe.title))
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton(resources.getString(R.string.yes)){ dialogInterface, _->
            mRecipeViewModel.delete(recipe)
            dialogInterface.dismiss()
        }
        builder.setNegativeButton(resources.getString(R.string.no)){dialogInterface, _->
            dialogInterface.dismiss()

        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }

    private fun filterRecipesListDialog(){
        mCustomListDialog = Dialog(requireActivity())
        val binding: DialogCustomListBinding = DialogCustomListBinding.inflate(layoutInflater)
        mCustomListDialog.setContentView(binding.root)
        binding.tvTitle.text = resources.getString(R.string.action_filter)
        val dishTypes = Constants.dishTypes()
        dishTypes.add(0,Constants.ALL_ITEMS)
        binding.rvList.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = CustomListItemAdapter(requireActivity(), this@AllRecipesFragment, dishTypes, Constants.FILTER_SELECTION)
        binding.rvList.adapter = adapter
        mCustomListDialog.show()
    }
    
    fun filterSelection(filterActionSelection: String){
        mCustomListDialog.dismiss()
        Log.i("Filter Selection", filterActionSelection)
        if(filterActionSelection == Constants.ALL_ITEMS){
            mRecipeViewModel.allRecipesList.observe(viewLifecycleOwner){
                    recipes ->
                recipes.let{
                    if(it.isNotEmpty()){
                        mBinding.rvRecipesList.visibility = View.VISIBLE
                        mBinding.tvNoDishesAddedYet.visibility = View.GONE
                        mRecipeAdapter.recipesList(it)
                    } else {
                        mBinding.rvRecipesList.visibility = View.GONE
                        mBinding.tvNoDishesAddedYet.visibility = View.VISIBLE
                    }
                }
            }
        } else {
            mRecipeViewModel.getFilteredList(filterActionSelection).observe(viewLifecycleOwner){
                recipes ->
                recipes.let{
                    if(it.isNotEmpty()){
                        mBinding.rvRecipesList.visibility = View.VISIBLE
                        mBinding.tvNoDishesAddedYet.visibility = View.GONE
                        mRecipeAdapter.recipesList(it)
                    } else {
                        mBinding.rvRecipesList.visibility = View.GONE
                        mBinding.tvNoDishesAddedYet.visibility = View.VISIBLE
                    }
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        if(requireActivity() is MainActivity){
            (activity as MainActivity?)?.showBottomNavigationView()
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
            R.id.action_filter -> {
                filterRecipesListDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}