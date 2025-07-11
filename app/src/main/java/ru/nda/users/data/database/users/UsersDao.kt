package ru.nda.users.data.database.users

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nda.users.data.database.users.dbo.UserDbo

@Dao
interface UsersDao {
    @Query("select * from users")
    fun getUsers(): Flow<List<UserDbo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUsers(users: List<UserDbo>)
}