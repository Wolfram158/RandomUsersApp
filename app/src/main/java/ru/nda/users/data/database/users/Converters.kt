package ru.nda.users.data.database.users

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.nda.users.data.database.users.dbo.DobDbo
import ru.nda.users.data.database.users.dbo.LocationDbo
import ru.nda.users.data.database.users.dbo.LoginDbo
import ru.nda.users.data.database.users.dbo.NameDbo
import ru.nda.users.data.database.users.dbo.PictureDbo
import ru.nda.users.data.database.users.dbo.RegisteredDbo
import javax.inject.Inject

@ProvidedTypeConverter
class Converters @Inject constructor(
    private val gson: Gson
) {
    @TypeConverter
    fun getLocationDbo(str: String): LocationDbo {
        return gson.fromJson(
            str,
            object : TypeToken<LocationDbo>() {}.type
        )
    }

    @TypeConverter
    fun saveLocationDbo(locationDbo: LocationDbo): String {
        return gson.toJson(locationDbo)
    }

    @TypeConverter
    fun getNameDbo(str: String): NameDbo {
        return gson.fromJson(
            str,
            object : TypeToken<NameDbo>() {}.type
        )
    }

    @TypeConverter
    fun saveNameDbo(nameDbo: NameDbo): String {
        return gson.toJson(nameDbo)
    }

    @TypeConverter
    fun getLoginDbo(str: String): LoginDbo {
        return gson.fromJson(
            str,
            object : TypeToken<LoginDbo>() {}.type
        )
    }

    @TypeConverter
    fun saveLoginDbo(loginDbo: LoginDbo): String {
        return gson.toJson(loginDbo)
    }


    @TypeConverter
    fun getDobDbo(str: String): DobDbo {
        return gson.fromJson(
            str,
            object : TypeToken<DobDbo>() {}.type
        )
    }

    @TypeConverter
    fun saveDobDbo(dobDbo: DobDbo): String {
        return gson.toJson(dobDbo)
    }


    @TypeConverter
    fun getRegisteredDbo(str: String): RegisteredDbo {
        return gson.fromJson(
            str,
            object : TypeToken<RegisteredDbo>() {}.type
        )
    }

    @TypeConverter
    fun saveRegisteredDbo(registeredDbo: RegisteredDbo): String {
        return gson.toJson(registeredDbo)
    }


    @TypeConverter
    fun getPictureDbo(str: String): PictureDbo {
        return gson.fromJson(
            str,
            object : TypeToken<PictureDbo>() {}.type
        )
    }

    @TypeConverter
    fun savePictureDbo(pictureDbo: PictureDbo): String {
        return gson.toJson(pictureDbo)
    }
}