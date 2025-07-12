package ru.nda.users.data.repository.user

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.core.net.toUri
import ru.nda.users.domain.repository.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val context: Context
) : UserRepository {
    override fun showMap(latitude: String, longitude: String) {
        Intent(Intent.ACTION_VIEW).apply {
            setData(
                listOf(latitude, longitude).joinToString(
                    separator = ",",
                    prefix = "geo:"
                ).toUri()
            )
            addFlags(FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }
    }

}