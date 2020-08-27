package com.shunsukeshoji.recipeapp.presentation.item

import android.view.View
import com.shunsukeshoji.recipeapp.R
import com.shunsukeshoji.recipeapp.databinding.ItemStatefulButtonBinding
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem

class StatefulButtonItem(
    val title: String,
    val state: Boolean,
    val onClick: (Boolean) -> Unit
) : BindableItem<ItemStatefulButtonBinding>(title.hashCode().toLong()) {

    override fun initializeViewBinding(view: View): ItemStatefulButtonBinding =
        ItemStatefulButtonBinding.bind(view)

    override fun getLayout(): Int = R.layout.item_stateful_button

    override fun bind(viewBinding: ItemStatefulButtonBinding, position: Int) {
        with(viewBinding) {
            button.text = title
            button.isSelected = state
            button.setOnClickListener {
                it.isSelected = !it.isSelected
                onClick(it.isSelected)
            }
        }
    }

    override fun hasSameContentAs(other: Item<*>): Boolean {
        if (other !is StatefulButtonItem) return false
        return title == other.title && other.state == state
    }

    override fun getSpanSize(spanCount: Int, position: Int): Int = spanCount / 2
}