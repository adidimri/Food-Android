package com.example.food_android.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.food_android.models.FoodRecipe
import com.example.food_android.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}