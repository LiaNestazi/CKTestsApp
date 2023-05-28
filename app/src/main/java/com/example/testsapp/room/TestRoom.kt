package com.example.testsapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testsapp.models.Question

@Entity(tableName = "tests")
data class TestRoom(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String ="",
    var description: String="",
    var type: String = "",
    var question_count: Int = 0,
    var rating: Int = 0,
    var timeInMillis: Long = 0L,
    var author_id: String = "",
    var author_login: String = "",
    var questions: List<Question> = mutableListOf()
)
