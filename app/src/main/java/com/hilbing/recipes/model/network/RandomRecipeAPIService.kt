package com.hilbing.recipes.model.network

import android.util.Log
import com.hilbing.recipes.model.entities.RandomRecipe
import com.hilbing.recipes.utils.Constants
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RandomRecipeAPIService {

    private val api = Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(RandomRecipeAPI::class.java)

    fun getRandomRecipe(): Single<RandomRecipe.RandomRecipes> {

        return api.getRandomRecipe(Constants.API_KEY_VALUE, Constants.LIMIT_LICENSE_VALUE, Constants.TAGS_VALUE, Constants.NUMBER_VALUE)
    }


}