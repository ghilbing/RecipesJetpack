package com.hilbing.recipes.view.adapters


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hilbing.recipes.R
import com.hilbing.recipes.databinding.ItemDishLayoutBinding
import com.hilbing.recipes.model.entities.Recipes
import com.hilbing.recipes.utils.Constants
import com.hilbing.recipes.view.activities.AddUpdateDishActivity
import com.hilbing.recipes.view.fragments.AllRecipesFragment
import com.hilbing.recipes.view.fragments.FavoriteRecipesFragment

class RecipeAdapter(private val fragment: Fragment): RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    private var recipes: List<Recipes> = listOf()

    class ViewHolder(view: ItemDishLayoutBinding) : RecyclerView.ViewHolder(view.root){
        val ivDishImage = view.ivDishImage
        val ivTitle = view.tvDishTitle
        val ibMore = view.ibMore
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemDishLayoutBinding = ItemDishLayoutBinding.inflate(LayoutInflater.from(fragment.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        Glide.with(fragment).load(recipe.image).into(holder.ivDishImage)
        holder.ivTitle.setText(recipe.title)
        holder.itemView.setOnClickListener{
            if(fragment is AllRecipesFragment)
                fragment.recipeDetails(recipe)
            if(fragment is FavoriteRecipesFragment)
                fragment.recipeDetails(recipe)
        }
        holder.ibMore.setOnClickListener{
            val popup = PopupMenu(fragment.context, holder.ibMore)
            popup.menuInflater.inflate(R.menu.menu_adpater, popup.menu)
            popup.setOnMenuItemClickListener { 
                if(it.itemId == R.id.action_edit_recipe){
                    val intent = Intent(fragment.requireActivity(), AddUpdateDishActivity::class.java)
                    intent.putExtra(Constants.EXTRA_DISH_DETAILS, recipe)
                    fragment.requireActivity().startActivity(intent)
                }else if(it.itemId == R.id.action_delete_recipe){
                    if(fragment is AllRecipesFragment){
                        fragment.deleteRecipe(recipe)
                    }
                }
                true
            }
            popup.show()
        }
        if(fragment is AllRecipesFragment){
            holder.ibMore.visibility = View.VISIBLE
        } else if(fragment is FavoriteRecipesFragment){
            holder.ibMore.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun recipesList(list: List<Recipes>){
        recipes = list
        notifyDataSetChanged()
    }

}