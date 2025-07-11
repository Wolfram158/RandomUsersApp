package ru.nda.users.domain.state

sealed class State<T> {
    class Success<T>(val data: T) : State<T>()
    class Error<T> : State<T>()
}