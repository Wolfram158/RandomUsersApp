package ru.nda.paging.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.nda.paging.domain.Item
import ru.nda.paging.domain.Loading

internal class PagingDiffUtilCallback<T : Item>(
    private val areItemsTheSameFun: (oldItem: T, newItem: T) -> Boolean,
    private val areContentsTheSameFun: (oldItem: T, newItem: T) -> Boolean
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return when (oldItem) {
            is Error -> newItem is Error
            is Loading -> newItem is Loading
            else -> {
                areItemsTheSameFun(oldItem, newItem)
            }
        }
    }

    override fun areContentsTheSame(
        oldItem: T,
        newItem: T
    ): Boolean {
        return when (oldItem) {
            is Error -> newItem is Error
            is Loading -> newItem is Loading
            else -> {
                areContentsTheSameFun(oldItem, newItem)
            }
        }
    }
}