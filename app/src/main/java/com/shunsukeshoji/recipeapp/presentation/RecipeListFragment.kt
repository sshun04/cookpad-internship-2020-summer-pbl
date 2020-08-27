package com.shunsukeshoji.recipeapp.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shunsukeshoji.recipeapp.R
import com.shunsukeshoji.recipeapp.databinding.FragmentRecipeListBinding
import com.shunsukeshoji.recipeapp.presentation.item.RecipeItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.viewbinding.GroupieViewHolder

class RecipeListFragment : Fragment(R.layout.fragment_recipe_list) {

    val viewModel: RecipeViewModel by viewModels {
        RecipeViewModel.ViewModelFactory(
            requireActivity().application,
            navArgs<RecipeListFragmentArgs>().value.filter.toList()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentRecipeListBinding.bind(view)
        with(binding) {

            val groupAdapter = GroupAdapter<GroupieViewHolder<*>>().apply {
                spanCount = 2
            }

            recipeRecyclerView.adapter = groupAdapter
            recipeRecyclerView.layoutManager =
                GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false).apply {
                    spanSizeLookup = groupAdapter.spanSizeLookup
                }


            viewModel.recipe.observe(viewLifecycleOwner, Observer { recipeList ->
                val items = recipeList.map { recipe ->
                    RecipeItem(
                        recipe
                    ) { id ->
                        findNavController().navigate(
                            RecipeListFragmentDirections
                                .actionRecipeListFragmentToRecipeDetailActivity(id)
                        )
                    }
                }
                groupAdapter.update(items)
            })
        }
    }

}