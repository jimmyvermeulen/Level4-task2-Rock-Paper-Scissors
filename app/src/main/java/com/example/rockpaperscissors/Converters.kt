package com.example.rockpaperscissors

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun intToMove(value: Int?): Move? {
        return value?.let { Move.values()[it] }
    }

    @TypeConverter
    fun moveToInt(move: Move?): Int? {
        return move?.ordinal?.toInt()
    }

    @TypeConverter
    fun intToGameResult(value: Int?): GameResult? {
        return value?.let { GameResult.values()[it]}
    }

    @TypeConverter
    fun gameResultToInt(gameResult: GameResult?): Int? {
        return gameResult?.ordinal?.toInt()
    }
}
