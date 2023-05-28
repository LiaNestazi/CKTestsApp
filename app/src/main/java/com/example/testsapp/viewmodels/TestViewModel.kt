package com.example.testsapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testsapp.models.Test
import com.example.testsapp.room.TestDatabase
import com.example.testsapp.room.TestRepository
import com.example.testsapp.room.TestRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TestViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Test>>
    private val repository: TestRepository

    init {
        val testDAO = TestDatabase.getInstance(application).testDao()
        repository = TestRepository(testDAO)
        readAllData = repository.readAllData
    }

    fun addTest(test: Test){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTest(test)
        }
    }
    fun findTestById(id: String): Test?{
        var foundTest = Test()
        viewModelScope.launch {
            foundTest = repository.findTestById(id)!!
        }
        return foundTest
    }
}