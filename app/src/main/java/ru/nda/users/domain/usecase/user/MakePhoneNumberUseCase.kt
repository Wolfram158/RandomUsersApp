package ru.nda.users.domain.usecase.user

import ru.nda.users.domain.repository.user.UserRepository
import javax.inject.Inject

class MakePhoneNumberUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(phone: String) = repository.makePhoneNumber(phone)
}