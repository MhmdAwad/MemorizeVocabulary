package com.mhmdawad.memorizevocabulary.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mhmdawad.memorizevocabulary.domain.module.VocabularyModule

@Database(
    entities = [VocabularyModule::class],
    version = 1
)
abstract class MemorizeDatabase: RoomDatabase() {

    abstract fun getMemorizeDao(): MemorizeDao
}