package ru.nda.users

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.nda.users.data.database.users.AppDatabase
import ru.nda.users.data.database.users.Converters
import ru.nda.users.data.database.users.UsersDao
import ru.nda.users.data.database.users.dbo.CoordinatesDbo
import ru.nda.users.data.database.users.dbo.DobDbo
import ru.nda.users.data.database.users.dbo.GenderDbo
import ru.nda.users.data.database.users.dbo.LocationDbo
import ru.nda.users.data.database.users.dbo.LoginDbo
import ru.nda.users.data.database.users.dbo.NameDbo
import ru.nda.users.data.database.users.dbo.PictureDbo
import ru.nda.users.data.database.users.dbo.RegisteredDbo
import ru.nda.users.data.database.users.dbo.StreetDbo
import ru.nda.users.data.database.users.dbo.TimezoneDbo
import ru.nda.users.data.database.users.dbo.UserDbo
import kotlin.random.Random

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class UsersDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var usersDao: UsersDao

    private val intToGender =
        mapOf(0 to GenderDbo.MALE, 1 to GenderDbo.FEMALE, 2 to GenderDbo.UNDEFINED)
    private val allowedChars = ('0'..'9') + ('a'..'z') + ('A'..'Z')

    @Before
    fun before() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            AppDatabase::class.java
        ).addTypeConverter(Converters(Gson())).build()
        usersDao = db.getUsersDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun insertUsersTest() = runBlocking {
        generateUsers(7).let { users ->
            usersDao.insertUsers(users)
        }
        assertEquals(usersDao.getUsers().size, 7)
    }

    @Test
    fun deleteAllTest() = runBlocking {
        usersDao.apply {
            insertUsers(generateUsers(17))
        }
        usersDao.deleteAll()
        assertEquals(usersDao.getUsers().size, 0)
    }

    private fun generateString(minLength: Int, maxLength: Int): String {
        return CharArray(
            Random.nextInt(
                minLength,
                maxLength + 1
            )
        ) { allowedChars.random() }.toString()
    }

    private fun generateBrandNewString(
        set: MutableSet<String>,
        minLength: Int,
        maxLength: Int
    ): String {
        while (true) {
            generateString(minLength, maxLength).let { str ->
                if (!set.contains(str)) {
                    set.add(str)
                    return str
                }
            }
        }
    }

    private fun generateUsers(count: Int): List<UserDbo> {
        val phones = mutableSetOf<String>()
        return mutableListOf<UserDbo>().also { users ->
            repeat(count) {
                users.add(
                    UserDbo(
                        genderDbo = intToGender[Random.nextInt(0, 3)]!!,
                        nameDbo = NameDbo(
                            firstName = generateString(8, 15),
                            lastName = generateString(8, 15)
                        ),
                        locationDbo = LocationDbo(
                            streetDbo = StreetDbo(
                                number = Random.nextInt(),
                                name = generateString(8, 15)
                            ),
                            city = generateString(8, 15),
                            state = generateString(8, 15),
                            country = generateString(8, 15),
                            postcode = generateString(8, 15),
                            coordinatesDbo = CoordinatesDbo(
                                latitude = generateString(9, 16),
                                longitude = generateString(8, 15)
                            ),
                            timezoneDbo = TimezoneDbo(
                                offset = generateString(8, 15),
                                description = generateString(8, 15)
                            )
                        ),
                        email = generateString(8, 15),
                        loginDbo = LoginDbo(
                            username = generateString(8, 15),
                            password = generateString(8, 15)
                        ),
                        dobDbo = DobDbo(
                            date = generateString(8, 15),
                            age = generateString(8, 15)
                        ),
                        registeredDbo = RegisteredDbo(
                            date = generateString(8, 15),
                            age = generateString(8, 15)
                        ),
                        pictureDbo = PictureDbo(
                            large = generateString(8, 15),
                            medium = generateString(8, 15),
                            thumbnail = generateString(8, 15)
                        ),
                        phone = generateBrandNewString(phones, 8, 16)
                    )
                )
            }
        }
    }

}