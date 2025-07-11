package ru.nda.users.data.database.users

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.nda.users.data.database.users.dbo.UserDbo

@TypeConverters(Converters::class)
@Database(entities = [UserDbo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "users.db"

        fun getInstance(context: Context): AppDatabase {
            instance?.let {
                return it
            }
            synchronized(LOCK) {
                instance?.let {
                    return it
                }
                return Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DB_NAME
                ).build().run {
                    instance = this
                    this
                }
            }
        }
    }

    abstract fun getUsersDao(): UsersDao
}