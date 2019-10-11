package com.example.rockpaperscissors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.random.Random

enum class GameResult{WIN, LOSE, DRAW}
enum class Move{ROCK, PAPER, SCISSORS}

class MainActivity : AppCompatActivity() {
    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle(R.string.app_name)
        gameRepository = GameRepository(this)
        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_view_game_history -> {
                startActivity(Intent(this@MainActivity, GameHistoryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    private fun initViews(){
        ivRock.setOnClickListener {
            playGame(Move.ROCK)
        }
        ivPaper.setOnClickListener {
            playGame(Move.PAPER)
        }
        ivScissor.setOnClickListener {
            playGame(Move.SCISSORS)
        }
        updateStatistics()

    }

    private fun playGame(playerMove : Move){
        var result : GameResult
        //generate random number
        var computerMove = Move.values()[Random.nextInt(0,3)]
        //compare moves
        if(playerMove == computerMove){
            result = GameResult.DRAW
        }
        else when(playerMove){
            Move.ROCK -> {
                if(computerMove == Move.SCISSORS)
                    result = GameResult.WIN
                else
                    result = GameResult.LOSE
            }
            Move.PAPER ->{
                if(computerMove == Move.ROCK)
                    result = GameResult.WIN
                else
                    result = GameResult.LOSE
            }
            Move.SCISSORS -> {
                if(computerMove == Move.PAPER)
                    result = GameResult.WIN
                else
                    result = GameResult.LOSE
            }
        }

        //show result on screen
        when(result){
            GameResult.WIN -> tvResult.text =  getString(R.string.you_win)
            GameResult.LOSE -> tvResult.text =  getString(R.string.computer_wins)
            GameResult.DRAW -> tvResult.text =  getString(R.string.draw)
        }
        when(computerMove){
            Move.ROCK -> ivComputer.setImageDrawable(getDrawable(R.drawable.rock))
            Move.PAPER -> ivComputer.setImageDrawable(getDrawable(R.drawable.paper))
            Move.SCISSORS -> ivComputer.setImageDrawable(getDrawable(R.drawable.scissors))
        }

        when(playerMove){
            Move.ROCK -> ivYou.setImageDrawable(getDrawable(R.drawable.rock))
            Move.PAPER -> ivYou.setImageDrawable(getDrawable(R.drawable.paper))
            Move.SCISSORS -> ivYou.setImageDrawable(getDrawable(R.drawable.scissors))
        }
        //store in database
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(Game(Date(), computerMove, playerMove, result))
            }
            updateStatistics()
        }


    }

    private fun updateStatistics(){
        CoroutineScope(Dispatchers.Main).launch {
            val wins = withContext(Dispatchers.IO) {
                gameRepository.getStat(GameResult.WIN)
            }

            val losses = withContext(Dispatchers.IO) {
                gameRepository.getStat(GameResult.LOSE)
            }

            val draws = withContext(Dispatchers.IO) {
                gameRepository.getStat(GameResult.DRAW)
            }

            tvStatistics.text = getString(R.string.statistics, wins, draws, losses)
        }
    }
}
