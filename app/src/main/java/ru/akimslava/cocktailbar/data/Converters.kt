package ru.akimslava.cocktailbar.data

import android.net.Uri
import androidx.core.net.toUri
import androidx.room.TypeConverter
import ru.akimslava.cocktailbar.domain.Cocktail

class Converters {
    @TypeConverter
    fun fromStringToList(string: String?): MutableList<String>? =
        string?.split(", ")?.toMutableList()

    @TypeConverter
    fun fromList(list: MutableList<String>?): String? =
        list?.joinToString()

    @TypeConverter
    fun fromUri(uri: Uri?): String? =
        uri?.toString()

    @TypeConverter
    fun fromStringToUri(string: String?): Uri? =
        string?.toUri()
}