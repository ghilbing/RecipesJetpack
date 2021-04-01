package com.hilbing.recipes.model.network

import com.hilbing.recipes.model.entities.RandomRecipe
import com.hilbing.recipes.utils.Constants
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import javax.security.auth.login.LoginException

interface RandomRecipeAPI {

    @GET(Constants.API_ENDPOINT)
    fun getRandomRecipe(
            @Query(Constants.API_KEY) apiKey: String,
            @Query(Constants.LIMIT_LICENSE) limitLicense: Boolean,
            @Query(Constants.TAGS) tags: String,
            @Query(Constants.NUMBER) number: Int

    ): Single<RandomRecipe.RandomRecipes>



}