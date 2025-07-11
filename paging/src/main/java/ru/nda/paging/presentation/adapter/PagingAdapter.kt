package ru.nda.paging.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.nda.paging.databinding.ItemErrorBinding
import ru.nda.paging.databinding.ItemLoadingBinding
import ru.nda.paging.domain.Error
import ru.nda.paging.domain.Item
import ru.nda.paging.domain.Loading
import kotlin.reflect.KClass

class PagingAdapter<T : Item>(
    private val classToBinding: MutableMap<KClass<*>, (ViewGroup) -> PagingViewHolder>,
    private val onClickTryButton: OnClickTryButton,
    areItemsTheSameFun: (oldItem: T, newItem: T) -> Boolean,
    areContentsTheSameFun: (oldItem: T, newItem: T) -> Boolean
) : ListAdapter<T, PagingViewHolder>(
    PagingDiffUtilCallback(
        areItemsTheSameFun = areItemsTheSameFun,
        areContentsTheSameFun = areContentsTheSameFun
    )
) {
    private val classToViewType =
        mutableMapOf<KClass<*>, Int>(
            Loading::class to LOADING_VIEW_TYPE,
            Error::class to ERROR_VIEW_TYPE
        )

    init {
        var index = BASE_VIEW_TYPE_COUNT
        classToBinding.forEach { entry ->
            classToViewType.put(entry.key, index)
            index++
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        return when (viewType) {
            LOADING_VIEW_TYPE -> {
                return LoadingViewHolder(
                    ItemLoadingBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            ERROR_VIEW_TYPE -> {
                ErrorViewHolder(
                    ItemErrorBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onClickTryButton
                )
            }

            else -> {
                classToViewType.forEach { entry ->
                    if (entry.value == viewType) {
                        return classToBinding[entry.key]?.invoke(parent)
                            ?: throw RuntimeException("Unexpected error")
                    }
                }
                throw RuntimeException("Unidentified viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        getItem(position).also { item -> holder.bind(item) }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)::class.run {
            classToViewType[this] ?: throw RuntimeException("Unidentified item class")
        }
    }

    fun interface OnClickTryButton {
        fun onClickTryButton()
    }

    companion object {
        private const val BASE_VIEW_TYPE_COUNT = 2
        private const val LOADING_VIEW_TYPE = 0
        private const val ERROR_VIEW_TYPE = 1
    }
}