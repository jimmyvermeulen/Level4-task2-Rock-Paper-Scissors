package com.example.rockpaperscissors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_game_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameHistoryActivity : AppCompatActivity() {
    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val games = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(games, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameRepository = GameRepository(this)
        setContentView(R.layout.activity_game_history)
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)

        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_game_history, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_delete_game_history -> {
                deleteGameHistory()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun initViews(){
        rvGameHistory.layoutManager = LinearLayoutManager(this@GameHistoryActivity, RecyclerView.VERTICAL, false)
        rvGameHistory.adapter = gameAdapter
        rvGameHistory.addItemDecoration(DividerItemDecoration(this@GameHistoryActivity, DividerItemDecoration.VERTICAL))
        getGameHistoryFromDatabase()

    }

    private fun deleteGameHistory(){
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()
            }
            getGameHistoryFromDatabase()
        }
    }

    private fun getGameHistoryFromDatabase(){
        CoroutineScope(Dispatchers.Main).launch {
            val games = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            this@GameHistoryActivity.games.clear()
            this@GameHistoryActivity.games.addAll(games)
            gameAdapter.notifyDataSetChanged()
        }
    }
}
