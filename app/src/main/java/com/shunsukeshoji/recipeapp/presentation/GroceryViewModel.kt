package com.shunsukeshoji.recipeapp.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shunsukeshoji.recipeapp.combine
import com.shunsukeshoji.recipeapp.data.GroceryDataRepository
import com.shunsukeshoji.recipeapp.model.Grocery
import com.shunsukeshoji.recipeapp.requireValue

class GroceryViewModel(application: Application) : AndroidViewModel(application) {
    private val groceryDataRepository =
        GroceryDataRepository(getApplication())

    data class UiModel(
        val list: List<GroceryModel>,
        val isNextActionEnable:Boolean
    )

    data class GroceryModel(
        val groupName: String,
        val nameWithState: Map<String, Boolean>
    )

    private val selectedGroceryList: MutableLiveData<List<String>> = MutableLiveData(listOf())
    private val groceryLiveData: MutableLiveData<List<Grocery>> = MutableLiveData(listOf())

    val uiModel: LiveData<UiModel> =
        combine(
            UiModel(listOf(),false),
            selectedGroceryList,
            groceryLiveData
        )
        { _: UiModel,
          selectedName: List<String>,
          baseList: List<Grocery> ->

            val result = baseList.map { element ->
                val nameWithState = element.content.map {
                    it to selectedName.contains(it)
                }.toMap()
                GroceryModel(
                    element.title,
                    nameWithState
                )
            }

            val isNextActionEnable = selectedName.isNotEmpty()

            UiModel(
                result,
               isNextActionEnable
            )
        }

    init {
        val list = groceryDataRepository.fetchAllGroceryList()
        groceryLiveData.value = list
    }

    fun onSelectItem(name: String, state: Boolean) {
        val current = selectedGroceryList.requireValue().toMutableList()
        if (state) {
            current.add(name)
        } else {
            current.remove(name)
        }
        selectedGroceryList.value = current
    }

    fun searchRecipe() {
        Log.d("GroceryViewModel", uiModel.requireValue().list.toString())
    }

    fun getGroceryList() = groceryDataRepository.fetchAllGroceryList()
}