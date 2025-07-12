package ru.nda.users.data.database.users

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.nda.users.data.database.users.dbo.UserDbo

@Dao
interface UsersDao {
    @Query("select * from users")
    suspend fun getUsers(): List<UserDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserDbo>)

    @Query("delete from users")
    suspend fun deleteAll()
}