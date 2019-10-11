package com.example.rockpaperscissors

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Game::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
public abstract class GameHistoryRoomDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao

    companion object {
        private const val DATABASE_NAME = "GAME_HISTORY_DATABASE"

        @Volatile
        private var gameHistoryRoomDatabaseInstance: GameHistoryRoomDatabase? = null

        fun getDatabase(context: Context): GameHistoryRoomDatabase? {
            if (gameHistoryRoomDatabaseInstance == null) {
                synchronized(GameHistoryRoomDatabase::class.java) {
                    if (gameHistoryRoomDatabaseInstance == null) {
                        gameHistoryRoomDatabaseInstance =
                            Room.databaseBuilder(context.applicationContext,GameHistoryRoomDatabase::class.java, DATABASE_NAME).build()
                    }
                }
            }
            return gameHistoryRoomDatabaseInstance
        }
    }
}
