package com.shunsukeshoji.recipeapp.data

import android.content.Context
import com.shunsukeshoji.recipeapp.model.Grocery
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class GroceryDataRepository(context: Context) {

    private val data: List<Grocery>

    init {
        val contentType = Types.newParameterizedType(List::class.java, Grocery::class.java)
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val jsonAdapter = moshi.adapter<List<Grocery>>(contentType)
        val jsonFile = context.assets.open("ingredients.json")
        val jsonString = readJson(jsonFile)
        data = jsonAdapter.fromJson(jsonString) ?: emptyList()
    }

    fun fetchAllGroceryList(): List<Grocery> = data

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