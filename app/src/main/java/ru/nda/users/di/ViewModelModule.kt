package ru.nda.users.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.nda.users.presentation.user.UserViewModel
import ru.nda.users.presentation.users.UsersViewModel

@Module
interface ViewModelModule {
    @ViewModelKey(UsersViewModel::class)
    @IntoMap
    @Binds
    fun bindUsersViewModel(viewModel: UsersViewModel): ViewModel

    @ViewModelKey(UserViewModel::class)
    @IntoMap
    @Binds
    fun bindUserViewModel(viewModel: UserViewModel): ViewModel
}