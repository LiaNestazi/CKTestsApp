package com.example.testsapp.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testsapp.models.Test
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestRepository(private val testDAO: TestDAO) {

    val readAllData: LiveData<List<Test>> = testDAO.getAll()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    suspend fun addTest(test: Test){
        testDAO.addTest(test)
    }
    fun findTestById(id: String): Test? {
        return testDAO.findById(id)
    }
}