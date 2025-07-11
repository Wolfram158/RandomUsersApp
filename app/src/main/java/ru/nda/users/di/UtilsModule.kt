package ru.nda.users.di

import android.content.Context
import coil.ImageLoader
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
class UtilsModule {
    @AppScope
    @Provides
    fun provideImageLoader(context: Context): ImageLoader {
        return ImageLoader(context)
    }

    @AppScope
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }
}