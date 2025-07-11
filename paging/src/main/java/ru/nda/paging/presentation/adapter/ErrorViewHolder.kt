package ru.nda.paging.presentation.adapter

import ru.nda.paging.databinding.ItemErrorBinding
import ru.nda.paging.domain.Item
import ru.nda.paging.presentation.adapter.PagingAdapter.OnClickTryButton

internal class ErrorViewHolder(
    private val binding: ItemErrorBinding,
    private val onClickTryButton: OnClickTryButton
) : PagingViewHolder(binding.root) {
    override fun <T : Item> bind(item: T) {
        binding.tryButton.setOnClickListener {
            onClickTryButton.onClickTryButton()
        }
    }
}