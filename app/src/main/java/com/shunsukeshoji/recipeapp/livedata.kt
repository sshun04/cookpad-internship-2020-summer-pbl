package com.shunsukeshoji.recipeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.distinctUntilChanged

fun <T : Any> LiveData<T>.requireValue() = requireNotNull(value)

inline fun <T : Any, LIVE1 : Any, LIVE2 : Any> combine(
    initialValue: T,
    liveData1: LiveData<LIVE1>,
    liveData2: LiveData<LIVE2>,
    crossinline block: (T, LIVE1, LIVE2) -> T
): LiveData<T> {
    return MediatorLiveData<T>().apply {
        postValue(initialValue)
        listOf(liveData1, liveData2).forEach { liveData ->
            addSource(liveData) {
                val currentValue = value
                val liveData1Value = liveData1.value
                val liveData2Value = liveData2.value
                if (currentValue != null && liveData1Value != null && liveData2Value != null) {
                    postValue(block(currentValue, liveData1Value, liveData2Value))
                }
            }
        }
    }.distinctUntilChanged()
}
