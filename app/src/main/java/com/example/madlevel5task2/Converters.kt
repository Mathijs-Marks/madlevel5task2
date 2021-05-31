package com.example.madlevel5task2

import androidx.room.TypeConverter
import java.util.*

class Converters {

    /**
     * This class converts string values to Date values, and vice versa.
     * Used to retrieve and store Date values from the database.
     */

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let {Date(it)}
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}