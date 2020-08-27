package com.shunsukeshoji.recipeapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shunsukeshoji.recipeapp.model.Grocery
import com.shunsukeshoji.recipeapp.presentation.GroceryPageFragment

class GroceryFragmentStateAdapter(
    fragmentActivity: FragmentActivity,
    val groceryList: List<Grocery>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = groceryList.count()

    override fun createFragment(position: Int): Fragment {
        val grocery = groceryList[position]
        return GroceryPageFragment.create(grocery)
    }
}