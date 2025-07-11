package ru.nda.users.presentation.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.coroutines.launch
import ru.nda.paging.presentation.adapter.PagingAdapter
import ru.nda.users.databinding.FragmentUsersBinding
import ru.nda.users.databinding.ItemUserBinding
import ru.nda.users.domain.entity.User
import ru.nda.users.domain.state.State
import ru.nda.users.presentation.App
import ru.nda.users.presentation.users.adapter.UserViewHolder
import ru.nda.users.presentation.vmfactory.ViewModelFactory
import javax.inject.Inject

class UsersFragment : Fragment() {
    private var _binding: FragmentUsersBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentUsersBinding is null")

    @Inject
    lateinit var userViewHolderFactory: UserViewHolder.UserViewHolderFactory

    private val adapter by lazy {
        PagingAdapter<User>(
            mutableMapOf(
                User::class to { parent ->
                    userViewHolderFactory.create(
                        ItemUserBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                    )
                }
            ),
            {},
            { new, old -> new == old },
            { new, old -> new == old }
        )
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val usersViewModel by lazy {
        ViewModelProvider.create(this, viewModelFactory)[UsersViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as App).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUsersRv()
        lifecycleScope.launch {
            usersViewModel.getUsers().collect {
                when (it) {
                    is State.Error<*> -> {}
                    is State.Success<*> -> {
                        adapter.submitList((it as State.Success<List<User>>).data)
                    }
                }
            }
        }
    }

    private fun setupUsersRv() {
        binding.usersRv.adapter = adapter
        binding.usersRv
            .addItemDecoration(
                DividerItemDecoration(context, LinearLayout.VERTICAL)
            )
    }

    companion object {
        fun newInstance() =
            UsersFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}