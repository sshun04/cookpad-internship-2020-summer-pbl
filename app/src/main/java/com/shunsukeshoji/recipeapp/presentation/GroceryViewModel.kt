package com.shunsukeshoji.recipeapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.shunsukeshoji.recipeapp.data.GroceryDataRepository
import com.shunsukeshoji.recipeapp.requireValue

class GroceryViewModel(application: Application) : AndroidViewModel(application) {
    private val groceryDataRepository =
        GroceryDataRepository(getApplication())

    val selectedGroceryList: MutableLiveData<List<String>> = MutableLiveData(listOf())

    fun onSelectItem(name: String, state: Boolean) {
        val current = selectedGroceryList.requireValue().toMutableList()
        if (state) {
            current.add(name)
        } else {
            current.remove(name)
        }
        selectedGroceryList.value = current
    }

    fun getGroceryList() = groceryDataRepository.fetchAllGroceryList()
}