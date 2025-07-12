package ru.nda.users.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.nda.paging.domain.Error
import ru.nda.paging.domain.Item
import ru.nda.paging.domain.Loading
import ru.nda.paging.presentation.adapter.PagingAdapter
import ru.nda.users.domain.usecase.users.DeleteAllUseCase
import ru.nda.users.domain.usecase.users.GetUsersFromDbUseCase
import ru.nda.users.domain.usecase.users.GetUsersFromNetUseCase
import ru.nda.users.domain.usecase.users.InsertUsersUseCase
import ru.nda.users.presentation.FirstError
import ru.nda.users.presentation.FirstProgress
import ru.nda.users.presentation.Initial
import ru.nda.users.presentation.Progress
import ru.nda.users.presentation.Result
import ru.nda.users.presentation.State
import javax.inject.Inject
import kotlin.math.ceil
import ru.nda.users.presentation.Error as Serror

class UsersViewModel @Inject constructor(
    private val getUsersFromNetUseCase: GetUsersFromNetUseCase,
    private val insertUsersUseCase: InsertUsersUseCase,
    private val getUsersFromDbUseCase: GetUsersFromDbUseCase,
    private val deleteAllUseCase: DeleteAllUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<State>(Initial)
    val state: StateFlow<State>
        get() = _state

    private val _data = mutableListOf<Item>()
    val data: List<Item>
        get() = _data

    private var page: Int = 1

    private var isGottenFromDb = false

    private var _isAllLoaded = false
    val isAllLoaded: Boolean
        get() = _isAllLoaded

    fun loadUsersPage(adapter: PagingAdapter<Item>) {
        viewModelScope.launch {
            if (_data.isNotEmpty() && _data.last() is Error) {
                _data.removeAt(_data.size - 1)
                adapter.submitList(_data)
                adapter.notifyItemRemoved(_data.size)
            }
            _state.value = if (page == 1) {
                FirstProgress
            } else {
                Progress
            }
            if (page > 1) {
                _data.add(Loading())
                adapter.submitList(_data)
                adapter.notifyItemInserted(_data.size)
            }
            _isAllLoaded = false
            try {
                val users = getUsersFromNetUseCase(page = page)
                insertUsersUseCase(users)
                if (users.isEmpty()) {
                    _isAllLoaded = true
                    return@launch
                }
                if (page > 1) {
                    _data.removeAt(_data.size - 1)
                    adapter.submitList(_data)
                    adapter.notifyItemRemoved(_data.size)
                }
                page++
                val oldSize = _data.size
                _data.addAll(users)
                adapter.submitList(_data)
                adapter.notifyItemRangeInserted(oldSize, users.size)
                _state.value = Result
            } catch (_: Exception) {
                val fail = try {
                    if (isGottenFromDb) {
                        throw Exception("true")
                    }
                    if (_data.isNotEmpty()) {
                        true
                    }
                    val savedUsers = getUsersFromDbUseCase()
                    if (savedUsers.isEmpty()) {
                        _state.value = FirstError
                        return@launch
                    }
                    val oldSize = _data.size
                    _data.addAll(savedUsers)
                    adapter.submitList(_data)
                    adapter.notifyItemRangeInserted(oldSize, savedUsers.size)
                    page = ceil(_data.size / PER_PAGE.toDouble()).toInt()
                    isGottenFromDb = true
                    _state.value = Result
                    false
                } catch (_: Exception) {
                    true
                }
                if (fail) {
                    _state.value = Serror
                    if (_data.isNotEmpty() && _data.last() is Loading) {
                        _data.removeAt(_data.size - 1)
                        _data.add(Error())
                        adapter.notifyItemInserted(_data.size)
                    }
                    if (_data.isEmpty()) {
                        _state.value = FirstError
                    }
                }
            }
        }
    }

    fun refresh(adapter: PagingAdapter<Item>) {
        viewModelScope.launch {
            deleteAllUseCase()
            page = 1
            _isAllLoaded = false
            isGottenFromDb = false
            _data.clear()
            adapter.submitList(_data)
            loadUsersPage(adapter)
        }
    }

    companion object {
        private const val PER_PAGE = 10
    }
}