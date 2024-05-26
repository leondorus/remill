package org.leondorus.remill.domain.model

import java.time.Duration
import java.time.LocalDateTime
import java.util.SortedSet

data class Schedule(
    val episodes: SortedSet<RepEpisode>
) : Sequence<LocalDateTime> {
    init {
        if (!this.areEpisodesCorrect(episodes)) {
            throw IllegalArgumentException("Episodes overlap")
        }
    }

    fun iteratorFrom(dateTime: LocalDateTime): Iterator<LocalDateTime> {
        TODO()
    }
    override fun iterator(): Iterator<LocalDateTime> {
        val tempSeq = sequence<LocalDateTime> {
            for (ep in episodes) {
                for (time in ep) {
                    yield(time)
                }
            }
        }
        return tempSeq.iterator()
    }

    private fun areEpisodesCorrect(episodes: SortedSet<RepEpisode>): Boolean {
        TODO()
    }
}

data class RepEpisode(
    val repeated: SortedSet<LocalDateTime>,
    val frequency: Duration,
    val numberOfTimes: NumberOfTimes
): Comparable<RepEpisode>, Sequence<LocalDateTime> {
    override fun iterator(): Iterator<LocalDateTime> {
        val tempSeq: Sequence<LocalDateTime> = if (numberOfTimes is NumberOfTimes.Infinite) {
            sequence {
                while(true) {
                    for (i in repeated)
                        yield(i)
                }
            }
        } else {
            numberOfTimes as NumberOfTimes.Finite
            sequence {
                for (i in 0u..<numberOfTimes.n) {
                    for (j in repeated) {
                        yield(j)
                    }
                }
            }
        }
        return tempSeq.iterator()
    }

    override fun compareTo(other: RepEpisode): Int {
        return when {
            this.repeated.isEmpty() && other.repeated.isEmpty() -> 0
            this.repeated.isEmpty() -> -1
            other.repeated.isEmpty() -> 1
            else -> this.repeated.first().compareTo(other.repeated.first())
        }
    }

//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (other !is RepEpisode) return false
//        if (this.repeated.isEmpty() && other.repeated.isEmpty()) return true
//        return this.repeated.first() == other.repeated.first()
//    }
//
//    override fun hashCode(): Int {
//        val firstElement = if (this.repeated.isEmpty())
//            LocalDateTime.MIN
//        else
//            this.repeated.first()
//
//        return firstElement.hashCode()
//    }
}

sealed interface NumberOfTimes {
    data class Finite(val n: UInt): NumberOfTimes
    data object Infinite : NumberOfTimes
}
