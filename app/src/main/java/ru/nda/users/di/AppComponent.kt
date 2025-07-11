package ru.nda.users.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.nda.users.presentation.user.UserFragment
import ru.nda.users.presentation.users.UsersFragment

@AppScope
@Component(
    modules = [
        NetworkModule::class,
        DatabaseModule::class,
        UtilsModule::class,
        ViewModelModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {

    fun inject(usersFragment: UsersFragment)

    fun inject(userFragment: UserFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}