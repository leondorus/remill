package org.leondorus.remill.database

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

private val gmtZoneId = ZoneId.of("GMT")
class LocalDateTimeConverter {
    @TypeConverter
    fun toTimestamp(ldt: LocalDateTime): Long {
        return ldt.atZone(gmtZoneId).toInstant().toEpochMilli()
    }

    @TypeConverter
    fun toLocalDateTime(timestamp: Long): LocalDateTime {
        return Instant.ofEpochMilli(timestamp).atZone(gmtZoneId).toLocalDateTime()
    }
}