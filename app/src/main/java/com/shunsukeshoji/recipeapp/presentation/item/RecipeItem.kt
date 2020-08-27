package com.shunsukeshoji.recipeapp.presentation.item

import android.view.View
import coil.api.load
import com.shunsukeshoji.recipeapp.R
import com.shunsukeshoji.recipeapp.databinding.ItemRecipeBinding
import com.shunsukeshoji.recipeapp.model.Recipe
import com.xwray.groupie.viewbinding.BindableItem

data class RecipeItem(val recipe: Recipe, val onClick: (Int) -> Unit) :
    BindableItem<ItemRecipeBinding>(recipe.id.toLong()) {
    override fun initializeViewBinding(view: View): ItemRecipeBinding = ItemRecipeBinding.bind(view)

    override fun getLayout(): Int = R.layout.item_recipe

    override fun bind(viewBinding: ItemRecipeBinding, position: Int) {
        with(viewBinding) {
            thumbnail.load(recipe.imageUrl)
            recipeTitle.text = recipe.title
            difficultyRateBar.rating = recipe.difficulty.toFloat()

            root.setOnClickListener {
                onClick(recipe.id)
            }
        }
    }

    override fun getSpanSize(spanCount: Int, position: Int): Int = spanCount / 2
}