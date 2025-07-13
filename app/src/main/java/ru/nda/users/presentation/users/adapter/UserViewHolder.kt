package ru.nda.users.presentation.users.adapter

import coil.ImageLoader
import coil.load
import coil.transform.CircleCropTransformation
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ru.nda.paging.domain.Item
import ru.nda.paging.presentation.adapter.PagingViewHolder
import ru.nda.users.R
import ru.nda.users.databinding.ItemUserBinding
import ru.nda.users.domain.entity.user.User

class UserViewHolder @AssistedInject constructor(
    private val imageLoader: ImageLoader,
    @Assisted private val binding: ItemUserBinding,
    @Assisted private val callback: (Any) -> Unit
) : PagingViewHolder(binding.root) {
    override fun <T : Item> bind(item: T) {
        (item as? User) ?: throw RuntimeException("User class is expected")
        with(binding) {
            photoIv.load(item.picture.large, imageLoader) {
                error(R.drawable.undefined)
                transformations(CircleCropTransformation())
            }
            fioTv.text = String.format(
                "Name: %s %s",
                item.name.firstName,
                item.name.lastName
            )
            phoneTv.text = String.format("Phone: %s", item.phone)
            addressTv.text = String.format(
                "Address: %s, %s, %s, %s, %s",
                item.location.country,
                item.location.city,
                item.location.street.name,
                item.location.street.number,
                item.location.postcode
            )
            root.setOnClickListener {
                callback(item)
            }
        }
    }

    @AssistedFactory
    interface UserViewHolderFactory {
        fun create(binding: ItemUserBinding, callback: (Any) -> Unit): UserViewHolder
    }
}