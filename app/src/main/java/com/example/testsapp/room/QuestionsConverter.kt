package com.example.testsapp.room

import androidx.room.TypeConverter
import com.example.testsapp.models.Question
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class QuestionsConverter {
    @TypeConverter
    fun fromQuestions(questions: List<Question>): String {
        return Gson().toJson(questions)
    }

    @TypeConverter
    fun toQuestions(json: String): List<Question>{
        return try {
            Gson().fromJson<List<Question>>(json)
        } catch (e: Exception){
            listOf()
        }
    }
}

inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)