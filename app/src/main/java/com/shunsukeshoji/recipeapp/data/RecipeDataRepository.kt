package com.shunsukeshoji.recipeapp.data

import android.content.Context
import com.shunsukeshoji.recipeapp.model.Recipe
import com.shunsukeshoji.recipeapp.model.RecipeList
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class RecipeDataRepository(context: Context) {
    private val data: List<Recipe>

    init {
        val contentType = Types.newParameterizedType(List::class.java, Recipe::class.java)
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val jsonAdapter = moshi.adapter<List<Recipe>>(contentType)
        val jsonFile = context.assets.open("recipe.json")
        val jsonString = readJson(jsonFile)
        data = jsonAdapter.fromJson(jsonString) ?: emptyList()
    }

    fun fetchAllRecipeList(): RecipeList = RecipeList(data)

    private fun readJson(inputStream: InputStream): String {
        val input = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(input)
        val stringBuilder = StringBuilder()
        val line = bufferedReader.readLines()
        line.forEach {
            stringBuilder.append(it)
        }

        return stringBuilder.toString()
    }
}