package com.shunsukeshoji.recipeapp.presentation

import android.app.Application
import androidx.lifecycle.*
import com.shunsukeshoji.recipeapp.combine
import com.shunsukeshoji.recipeapp.data.RecipeDataRepository
import com.shunsukeshoji.recipeapp.model.RecipeList

class RecipeViewModel(application: Application, filter: List<String>) :
    AndroidViewModel(application) {

    private val recipeRepository = RecipeDataRepository(application)
    private val ingredientLiveData: MutableLiveData<List<String>> = MutableLiveData(filter)
    private val difficultyLiveData: MutableLiveData<List<Int>> = MutableLiveData(listOf(1))
    private val baseRecipeListLiveData: MutableLiveData<RecipeList> =
        MutableLiveData(RecipeList.EMPTY)

    val recipe: LiveData<RecipeList> = combine(
        initialValue = RecipeList.EMPTY,
        liveData1 = baseRecipeListLiveData,
        liveData2 = difficultyLiveData,
        liveData3 = ingredientLiveData
    ) { _: RecipeList,
        baseList: RecipeList,
        difficulty: List<Int>,
        ingredient: List<String> ->

        return@combine baseList.filtered(difficulty, ingredient)
    }

    init {
        baseRecipeListLiveData.value = recipeRepository.fetchAllRecipeList()
    }

    class ViewModelFactory(private val application: Application, private val filter: List<String>) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RecipeViewModel(application, filter) as T
        }
    }
}