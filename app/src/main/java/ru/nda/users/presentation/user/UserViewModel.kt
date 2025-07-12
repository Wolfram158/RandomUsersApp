package ru.nda.users.presentation.user

import androidx.lifecycle.ViewModel
import ru.nda.users.domain.usecase.user.MailtoUseCase
import ru.nda.users.domain.usecase.user.MakePhoneNumberUseCase
import ru.nda.users.domain.usecase.user.ShowMapUseCase
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val showMapUseCase: ShowMapUseCase,
    private val mailtoUseCase: MailtoUseCase,
    private val makePhoneNumberUseCase: MakePhoneNumberUseCase
) : ViewModel() {
    fun showMap(latitude: String, longitude: String) =
        showMapUseCase(latitude = latitude, longitude = longitude)

    fun mailto(email: String) = mailtoUseCase(email)

    fun makePhoneNumber(phone: String) = makePhoneNumberUseCase(phone)
}