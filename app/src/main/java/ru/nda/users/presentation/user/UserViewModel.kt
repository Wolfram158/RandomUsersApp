package ru.nda.users.presentation.user

import androidx.lifecycle.ViewModel
import ru.nda.users.domain.usecase.user.ShowMapUseCase
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val showMapUseCase: ShowMapUseCase
) : ViewModel() {
    fun showMap(latitude: String, longitude: String) =
        showMapUseCase(latitude = latitude, longitude = longitude)
}