package com.example.testsapp.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testsapp.models.Test

@Dao
interface TestDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTest(test: Test)

    @Query("SELECT * FROM tests")
    fun getAll(): LiveData<List<Test>>

    @Query("SELECT * FROM tests WHERE id = :id")
    fun findById(id: String): Test?
}