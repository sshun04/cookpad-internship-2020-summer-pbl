package com.shunsukeshoji.recipeapp.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.shunsukeshoji.recipeapp.R

class MainActivity : AppCompatActivity() {

    private val viewModel: GroceryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.toString()
    }
}
