package org.leondorus.remill.database

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

class LocalDateTimeConverter {
    @TypeConverter
    fun fromLocalDateTime(ldt: LocalDateTime): Long {
        return ldt.toEpochSecond(ZoneOffset.UTC)
    }

    @TypeConverter
    fun toLocalDateTime(long: Long): LocalDateTime {
        return LocalDateTime.ofEpochSecond(long, 0, ZoneOffset.UTC)
    }
}