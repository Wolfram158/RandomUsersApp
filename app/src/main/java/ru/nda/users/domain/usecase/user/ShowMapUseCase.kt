package ru.nda.users.domain.usecase.user

import ru.nda.users.di.AppScope
import ru.nda.users.domain.repository.user.UserRepository
import javax.inject.Inject

@AppScope
class ShowMapUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(latitude: String, longitude: String) =
        repository.showMap(latitude = latitude, longitude = longitude)
}