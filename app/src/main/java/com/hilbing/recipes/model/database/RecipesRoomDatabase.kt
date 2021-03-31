package com.hilbing.recipes.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hilbing.recipes.model.entities.Recipes

@Database(entities = [Recipes::class], version = 1)
abstract class RecipesRoomDatabase: RoomDatabase() {

    abstract fun recipeDao() : RecipesDAO

    companion object{
        @Volatile
        private var INSTANCE: RecipesRoomDatabase? = null

        fun getDatabase(context: Context): RecipesRoomDatabase {
            //if the INSTANCE is not null, then return it
            //if it is, then create the database
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipesRoomDatabase::class.java,
                    "recipes_database"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                //return instance
                instance
            }
        }
    }
}