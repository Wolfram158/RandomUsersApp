package ru.nda.paging.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.nda.paging.domain.Item

abstract class PagingViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
    abstract fun <T : Item> bind(item: T)
}