package com.mhmdawad.memorizevocabulary.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mhmdawad.memorizevocabulary.domain.module.VocabularyModule
import kotlinx.coroutines.flow.Flow

@Dao
interface MemorizeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewVocabulary(vocabularyModule: VocabularyModule): Long

    @Query("DELETE FROM vocabularyModule WHERE englishVocabulary = :vocabulary")
    suspend fun deleteSpecificVocabulary(vocabulary: String)

    @Query("DELETE FROM vocabularyModule")
    suspend fun deleteAllVocabularies()

    @Query("SELECT * FROM vocabularyModule")
    fun retrieveAllVocabularies(): Flow<List<VocabularyModule>>

}