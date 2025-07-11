package ru.nda.paging.presentation.adapter

import ru.nda.paging.databinding.ItemLoadingBinding
import ru.nda.paging.domain.Item

internal class LoadingViewHolder(
    binding: ItemLoadingBinding
) : PagingViewHolder(binding.root) {
    override fun <T : Item> bind(item: T) {
    }
}