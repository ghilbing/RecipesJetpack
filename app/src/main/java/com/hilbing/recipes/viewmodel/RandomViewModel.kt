package com.hilbing.recipes.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hilbing.recipes.model.entities.RandomRecipe
import com.hilbing.recipes.model.network.RandomRecipeAPIService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class RandomViewModel : ViewModel() {
    private val randomRecipeAPIService = RandomRecipeAPIService()
    private val compositeDisposable = CompositeDisposable()

    val loadRandomRecipe = MutableLiveData<Boolean>()
    val randomRecipeResponse = MutableLiveData<RandomRecipe.RandomRecipes>()
    val randomRecipeLoadingError = MutableLiveData<Boolean>()



    fun getRandomRecipeFromAPI(){

        loadRandomRecipe.value = true
        compositeDisposable.add(
            randomRecipeAPIService.getRandomRecipe()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :

                    DisposableSingleObserver<RandomRecipe.RandomRecipes>() {

                    override fun onSuccess(value: RandomRecipe.RandomRecipes?) {
                        loadRandomRecipe.value = false
                        randomRecipeResponse.value = value
                        randomRecipeLoadingError.value = false
                    }

                    override fun onError(e: Throwable?) {
                        loadRandomRecipe.value = false
                        randomRecipeLoadingError.value = true
                        e!!.printStackTrace()
                        Log.i("E printStackTrace", e.message.toString())
                    }

                })
        )
    }


}