package com.shunsukeshoji.recipeapp.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Grocery(
    @Json(name = "field_name") val fieldName: String,
    val title: String,
    val content: List<String>
) : Parcelable