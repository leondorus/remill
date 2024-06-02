package org.leondorus.remill.domain.model

import java.time.LocalDateTime

data class Schedule(
    val times: List<LocalDateTime>
): Sequence<LocalDateTime> {
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
