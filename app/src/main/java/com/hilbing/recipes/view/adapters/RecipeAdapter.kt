package com.hilbing.recipes.view.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hilbing.recipes.databinding.ItemDishLayoutBinding
import com.hilbing.recipes.model.entities.Recipes

class RecipeAdapter(private val fragment: Fragment): RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    private var recipes: List<Recipes> = listOf()

    class ViewHolder(view: ItemDishLayoutBinding) : RecyclerView.ViewHolder(view.root){
        val ivDishImage = view.ivDishImage
        val ivTitle = view.tvDishTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemDishLayoutBinding = ItemDishLayoutBinding.inflate(LayoutInflater.from(fragment.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        Glide.with(fragment).load(recipe.image).into(holder.ivDishImage)
        holder.ivTitle.setText(recipe.title)

    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun recipesList(list: List<Recipes>){
        recipes = list
        notifyDataSetChanged()
    }

}