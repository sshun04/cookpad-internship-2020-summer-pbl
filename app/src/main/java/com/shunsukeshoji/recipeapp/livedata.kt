package com.shunsukeshoji.recipeapp

import androidx.lifecycle.LiveData

fun <T : Any> LiveData<T>.requireValue() = requireNotNull(value)