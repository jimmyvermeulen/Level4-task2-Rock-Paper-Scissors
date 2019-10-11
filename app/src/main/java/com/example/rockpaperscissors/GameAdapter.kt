package com.example.rockpaperscissors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.history_item.view.*

class GameAdapter(private val gameList : List<Game>, private val context: Context) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(gameList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(game: Game) {
            itemView.tvDateTime.text = game.date.toString()
            when(game.result){
                GameResult.WIN -> itemView.tvResult.text =  context.getString(R.string.you_win)
                GameResult.LOSE -> itemView.tvResult.text =  context.getString(R.string.computer_wins)
                GameResult.DRAW -> itemView.tvResult.text =  context.getString(R.string.draw)
            }
            when(game.computerMove){
                Move.ROCK -> itemView.ivComputer.setImageDrawable(context.getDrawable(R.drawable.rock))
                Move.PAPER -> itemView.ivComputer.setImageDrawable(context.getDrawable(R.drawable.paper))
                Move.SCISSORS -> itemView.ivComputer.setImageDrawable(context.getDrawable(R.drawable.scissors))
            }

            when(game.playerMove){
                Move.ROCK -> itemView.ivYou.setImageDrawable(context.getDrawable(R.drawable.rock))
                Move.PAPER -> itemView.ivYou.setImageDrawable(context.getDrawable(R.drawable.paper))
                Move.SCISSORS -> itemView.ivYou.setImageDrawable(context.getDrawable(R.drawable.scissors))
            }

        }
    }


}