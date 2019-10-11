package com.example.rockpaperscissors

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface GameDao {

    @Query("SELECT * FROM game_table")
    suspend fun getAllGames(): List<Game>

    @Insert
    suspend fun insertGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)

    @Query("DELETE FROM game_table")
    suspend fun deleteAllGames()

    @Query("SELECT COUNT(*) FROM game_table WHERE result = :gameResult")
    suspend fun getStat(gameResult: GameResult) : Int

}
