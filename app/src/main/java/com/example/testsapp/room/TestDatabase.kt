package com.example.testsapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.testsapp.models.Test

@Database(entities = [Test::class], version = 1, exportSchema = false)
@TypeConverters(QuestionsConverter::class)
abstract class TestDatabase: RoomDatabase() {
    abstract fun testDao(): TestDAO
    companion object{
        @Volatile
        private var INSTANCE: TestDatabase? = null

        fun getInstance(context: Context): TestDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TestDatabase::class.java,
                    "test_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}