package com.shunsukeshoji.recipeapp.presentation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shunsukeshoji.recipeapp.R
import com.shunsukeshoji.recipeapp.databinding.FragmentGroceryPageBinding
import com.shunsukeshoji.recipeapp.model.Grocery
import com.shunsukeshoji.recipeapp.presentation.item.StatefulButtonItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.viewbinding.GroupieViewHolder

class GroceryPageFragment : Fragment(R.layout.fragment_grocery_page) {
    companion object {
        fun create(grocery: Grocery): GroceryPageFragment {
            return GroceryPageFragment()
                .apply {
                    this.arguments =
                        createBundle(
                            grocery
                        )
                }
        }

        private fun createBundle(grocery: Grocery): Bundle =
            bundleOf(GROCERY_KEY to grocery)

        private const val GROCERY_KEY = "key_grocery"
    }

    private val viewModel: GroceryViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentGroceryPageBinding.bind(view)
        val baseElement = arguments?.get(GROCERY_KEY) as? Grocery ?: return

        with(binding) {
            val groupAdapter = GroupAdapter<GroupieViewHolder<*>>().apply {
                spanCount = 2
            }

            recyclerView.adapter = groupAdapter
            recyclerView.layoutManager =
                GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false).apply {
                    spanSizeLookup = groupAdapter.spanSizeLookup
                }

            val items = baseElement.content.map { name ->
                StatefulButtonItem(name, false) { isSelected ->
                    viewModel.onSelectItem(name, isSelected)
                }
            }

            groupAdapter.update(items)
        }
    }
}