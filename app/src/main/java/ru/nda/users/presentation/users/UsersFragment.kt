package ru.nda.users.presentation.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.nda.paging.domain.Item
import ru.nda.paging.presentation.adapter.PagingAdapter
import ru.nda.users.R
import ru.nda.users.databinding.FragmentUsersBinding
import ru.nda.users.databinding.ItemUserBinding
import ru.nda.users.domain.entity.user.User
import ru.nda.users.presentation.App
import ru.nda.users.presentation.Error
import ru.nda.users.presentation.FirstError
import ru.nda.users.presentation.FirstProgress
import ru.nda.users.presentation.Initial
import ru.nda.users.presentation.Progress
import ru.nda.users.presentation.Result
import ru.nda.users.presentation.user.UserFragment
import ru.nda.users.presentation.users.adapter.UserViewHolder
import ru.nda.users.presentation.vmfactory.ViewModelFactory
import javax.inject.Inject

class UsersFragment : Fragment() {
    private var _binding: FragmentUsersBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentUsersBinding is null")

    @Inject
    lateinit var userViewHolderFactory: UserViewHolder.UserViewHolderFactory

    private val usersAdapter: PagingAdapter<Item> by lazy {
        PagingAdapter<Item>(
            mutableMapOf(
                User::class to { parent ->
                    userViewHolderFactory.create(
                        ItemUserBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                    ) { maybeUser ->
                        launchUserFragment(maybeUser)
                    }
                }
            ),
            {
                usersViewModel.loadUsersPage(usersAdapter)
            },
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

        observeUsersState()
        setupUsersRv()
        setupRefreshLayout()

        if (savedInstanceState == null) {
            usersViewModel.loadUsersPage(usersAdapter)
        } else {
            usersAdapter.submitList(usersViewModel.data)
        }
    }

    private fun launchUserFragment(maybeUser: Any) {
        if (maybeUser !is User) {
            throw RuntimeException("Unexpected error")
        }
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                UserFragment.newInstance(maybeUser),
                null
            )
            .addToBackStack(null)
            .commit()
    }

    private fun observeUsersState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                usersViewModel.state.collectLatest { state ->
                    if (state !is FirstProgress) {
                        binding.progressBar.visibility = View.GONE
                    }
                    if (state !is FirstError) {
                        binding.errorLayout.visibility = View.GONE
                    }
                    if (state is FirstProgress || state is FirstError) {
                        binding.usersRv.visibility = View.GONE
                    } else {
                        binding.usersRv.visibility = View.VISIBLE
                    }
                    when (state) {
                        Error -> {}
                        FirstError -> {
                            binding.errorLayout.visibility = View.VISIBLE
                            binding.tryButton.setOnClickListener {
                                usersViewModel.loadUsersPage(usersAdapter)
                            }
                        }

                        FirstProgress -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        Initial -> {}
                        Progress -> {}
                        Result -> {}
                    }
                }
            }
        }
    }

    private fun setupUsersRv() {
        with(binding.usersRv) {
            adapter = usersAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    layoutManager?.let {
                        val visibleItemCount = it.childCount
                        val totalItemCount = it.itemCount
                        val firstVisibleItemPosition =
                            (layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
                                ?: throw RuntimeException("LinearLayoutManager is expected")
                        if (usersViewModel.state.value !is FirstProgress &&
                            usersViewModel.state.value !is Progress &&
                            usersViewModel.state.value !is Error &&
                            !usersViewModel.isAllLoaded
                        ) {
                            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                                && firstVisibleItemPosition >= 0
                            ) {
                                usersViewModel.loadUsersPage(usersAdapter)
                            }
                        }
                    }
                }
            })
        }
    }

    private fun setupRefreshLayout() {
        binding.refreshLayout.apply {
            setOnRefreshListener {
                usersViewModel.refresh(usersAdapter)
                isRefreshing = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val USERS_FRAGMENT = "UsersFragment"

        fun newInstance() =
            UsersFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}