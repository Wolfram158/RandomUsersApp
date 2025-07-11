package ru.nda.users.di

import dagger.Binds
import dagger.Module
import ru.nda.users.data.repository.users.UsersRepositoryImpl
import ru.nda.users.domain.repository.users.UsersRepository

@Module
interface RepositoryModule {
    @AppScope
    @Binds
    fun bindUsersRepository(impl: UsersRepositoryImpl): UsersRepository
}