package ru.akimslava.cocktailbar.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromStringToList(string: String?): MutableList<String>? =
        string?.split(", ")?.toMutableList()

    @TypeConverter
    fun fromList(list: MutableList<String>?): String? =
        list?.joinToString()
}