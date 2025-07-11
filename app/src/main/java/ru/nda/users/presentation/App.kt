package ru.nda.users.presentation

import android.app.Application
import ru.nda.users.di.DaggerAppComponent

class App : Application() {
    private val _component by lazy {
        DaggerAppComponent
            .factory()
            .create(applicationContext)
    }

    val component
        get() = _component
}