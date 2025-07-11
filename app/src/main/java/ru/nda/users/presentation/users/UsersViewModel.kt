package ru.nda.users.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.stateIn
import ru.nda.users.domain.usecase.users.GetUsersUseCase
import javax.inject.Inject

class UsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {
    suspend fun getUsers() = getUsersUseCase(1).stateIn(viewModelScope)

}