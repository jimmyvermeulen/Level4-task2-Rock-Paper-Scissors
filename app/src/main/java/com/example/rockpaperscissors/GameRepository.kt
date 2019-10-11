package com.example.rockpaperscissors

import android.content.Context

class GameRepository(context: Context) {

    private val gameDao: GameDao

    init {
        val database = GameHistoryRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    suspend fun getAllGames(): List<Game> = gameDao.getAllGames()

    suspend fun insertGame(game: Game) = gameDao.insertGame(game)

    suspend fun deleteGame(game: Game) = gameDao.deleteGame(game)

    suspend fun deleteAllGames() = gameDao.deleteAllGames()

    suspend fun getStat(gameResult: GameResult): Int = gameDao.getStat(gameResult)

}
