package com.example.madlevel5task2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "gameTable")
data class Game (

    @ColumnInfo(name = "title")
    var gameTitleText: String,

    @ColumnInfo(name = "platform")
    var gamePlatformText: String,

    @ColumnInfo(name = "release_date")
    var gameReleaseDate: Date,

    @ColumnInfo(name = "release_date_text")
    var gameReleaseDateText: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
)