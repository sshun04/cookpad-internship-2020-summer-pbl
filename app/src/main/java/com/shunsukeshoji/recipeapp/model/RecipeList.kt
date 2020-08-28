package com.shunsukeshoji.recipeapp.model

class RecipeList(val list: List<Recipe>) : List<Recipe> by list {

    fun filtered(difficulty: List<Int>, ingredient: List<String>): RecipeList {
        val filteredList = filter { recipe ->
            difficulty.contains(recipe.difficulty)
        }.filter { recipe ->
            recipe.ingredients.containsAll(ingredient)
//            recipe.ingredients.any { ingredient.contains(it) }
        }
        return RecipeList(filteredList)
    }

    companion object {
        val EMPTY =
            RecipeList(listOf())
    }
}