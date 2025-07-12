package ru.nda.users.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import ru.nda.users.data.database.users.AppDatabase
import ru.nda.users.data.database.users.UsersDao

@Module
class DatabaseModule {
    @AppScope
    @Provides
    fun provideUsersDao(context: Context, gson: Gson): UsersDao {
        return AppDatabase.getInstance(context, gson).getUsersDao()
    }

}