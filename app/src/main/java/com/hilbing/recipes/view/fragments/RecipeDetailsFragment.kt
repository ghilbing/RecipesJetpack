package com.hilbing.recipes.view.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.hilbing.recipes.R
import com.hilbing.recipes.application.RecipeApplication
import com.hilbing.recipes.databinding.FragmentRecipeDetailsBinding
import com.hilbing.recipes.viewmodel.RecipeViewModelFactory
import com.hilbing.recipes.viewmodel.RecipesViewModel
import java.io.IOException
import java.util.*

class RecipeDetailsFragment : Fragment() {

    private var mBinding: FragmentRecipeDetailsBinding? = null
    private val mRecipesViewModel: RecipesViewModel by viewModels {
        RecipeViewModelFactory(((requireActivity().application) as RecipeApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: RecipeDetailsFragmentArgs by navArgs()
        args.let {
            try{
                Glide.with(requireActivity())
                    .load(it.recipeDetails.image)
                    .centerCrop()
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            Log.i("TAG", "onLoadFailed: error loading image")
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            resource.let {
                                Palette.from(resource!!.toBitmap()).generate(){
                                        palette ->
                                    val intColor = palette?.vibrantSwatch?.rgb ?: 0
                                    mBinding!!.svRecipeDetail.setBackgroundColor(intColor)
                                }
                            }
                            return false
                        }
                    })
                    .into(mBinding!!.ivDishImage)

            } catch (e: IOException){
                e.printStackTrace()
            }
            mBinding!!.tvTitle.text = it.recipeDetails.title
            mBinding!!.tvType.text = it.recipeDetails.type.capitalize(Locale.ROOT)
            mBinding!!.tvCategory.text = it.recipeDetails.category
            mBinding!!.tvIngredients.text = it.recipeDetails.ingredients
            mBinding!!.tvCookingDirection.text = it.recipeDetails.directionToCook
            mBinding!!.tvCookingTime.text = resources.getString(R.string.lbl_estimate_cooking_time, it.recipeDetails.cookingTime)

            if(args.recipeDetails.favoriteDish){
                mBinding!!.ivFavoriteDish.setImageDrawable(ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_favorite_selected
                ))
            } else {
                mBinding!!.ivFavoriteDish.setImageDrawable(ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_favorite_unselected
                ))
            }
        }

        mBinding!!.ivFavoriteDish.setOnClickListener{
            args.recipeDetails.favoriteDish = !args.recipeDetails.favoriteDish
            mRecipesViewModel.update(args.recipeDetails)
            if(args.recipeDetails.favoriteDish){
                mBinding!!.ivFavoriteDish.setImageDrawable(ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_favorite_selected
                ))
                Toast.makeText(requireActivity(), resources.getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show()
            } else {
                mBinding!!.ivFavoriteDish.setImageDrawable(ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_favorite_unselected
                ))
                Toast.makeText(requireActivity(), resources.getString(R.string.removed_from_favorites), Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }


}