package com.example.rockpaperscissors

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*




@Entity(tableName = "game_table")
@Parcelize
public data class Game(

    @ColumnInfo(name = "date")
    var date: Date,

    @ColumnInfo(name = "computer_move")
    var computerMove: Move,

    @ColumnInfo(name = "player_move")
    var playerMove: Move,

    @ColumnInfo(name = "result")
    var result: GameResult,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

) : Parcelable
