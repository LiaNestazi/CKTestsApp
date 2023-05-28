package com.example.testsapp.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

@Suppress("UNCHECKED_CAST")
class TestViewModelFactory(private var application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T{
        if (modelClass.isAssignableFrom(TestViewModel::class.java)){
            return TestViewModel(application) as T
        }
        throw IllegalArgumentException("View Model class is not found.")
    }
}