package com.example.testsapp.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tests")
data class Test(
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name="id")
    var id: String = "",
    @ColumnInfo(name="name")
    var name: String ="",
    @ColumnInfo(name="description")
    var description: String="",
    @ColumnInfo(name="type")
    var type: String = "",
    @ColumnInfo(name="question_count")
    var question_count: Int = 0,
    @ColumnInfo(name="rating")
    var rating: Int = 0,
    @ColumnInfo(name="timeInMillis")
    var timeInMillis: Long = 0L,
    @ColumnInfo(name="author_id")
    var author_id: String = "",
    @ColumnInfo(name="author_login")
    var author_login: String = "",
    @ColumnInfo(name="questions")
    var questions: List<Question> = mutableListOf()
)
