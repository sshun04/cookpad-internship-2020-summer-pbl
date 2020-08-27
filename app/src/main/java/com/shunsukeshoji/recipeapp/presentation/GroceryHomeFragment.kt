package com.shunsukeshoji.recipeapp.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.shunsukeshoji.recipeapp.GroceryFragmentStateAdapter
import com.shunsukeshoji.recipeapp.R
import com.shunsukeshoji.recipeapp.databinding.FragmentGroceryHomeBinding

class GroceryHomeFragment : Fragment(R.layout.fragment_grocery_home) {
    private val viewModel: GroceryViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentGroceryHomeBinding.bind(view)
        with(binding) {
            val groceryList = viewModel.getGroceryList()
            val pagerAdapter =
                GroceryFragmentStateAdapter(
                    requireActivity(),
                    groceryList
                )
            viewPager.apply {
                adapter = pagerAdapter
            }
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = groceryList[position].title
            }.attach()
        }
    }
}