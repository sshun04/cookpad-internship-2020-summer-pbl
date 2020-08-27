package com.shunsukeshoji.recipeapp.model

import com.squareup.moshi.Json

data class Recipe(
    val id: Int,
    val title: String,
    @Json(name = "image") val imageUrl: String,
    val steps: List<String>,
    val ingredients: List<String>,
    val description: String,
    val calorie: Int,
    val difficulty: Int
)