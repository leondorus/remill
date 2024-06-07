package org.leondorus.remill.domain.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class Schedule(
    val times: List<@Serializable(with = LocalDateTimeSerializer::class) LocalDateTime>,
) : Sequence<LocalDateTime> {
    init {
        val timesSorted = times.sorted()
        if (times != timesSorted) {
            throw IllegalArgumentException("Times is not sorted")
        }
    }

    override fun iterator(): Iterator<LocalDateTime> {
        return times.iterator()
    }

    fun iteratorFrom(ldt: LocalDateTime): Iterator<LocalDateTime> {
        var first: Int = times.size
        for (i in times.indices) {
            if (times[i] >= ldt) {
                first = i
                break
            }
        }

        return sequence {
            for (i in first..times.size) {
                yield(times[i])
            }
        }.iterator()
    }
}

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = LocalDateTime::class)
object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.format(formatter))
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.parse(decoder.decodeString(), formatter)
    }
}
