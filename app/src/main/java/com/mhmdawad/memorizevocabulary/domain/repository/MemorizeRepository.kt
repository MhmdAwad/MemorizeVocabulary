package com.mhmdawad.memorizevocabulary.domain.repository

import com.mhmdawad.memorizevocabulary.domain.module.VocabularyModule
import kotlinx.coroutines.flow.Flow

interface MemorizeRepository {

    suspend fun insertNewVocabulary(vocabularyModule: VocabularyModule): Long
    suspend fun deleteSpecificVocabulary(vocabulary: String)
    suspend fun deleteAllVocabularies()
    fun retrieveAllVocabularies(): Flow<List<VocabularyModule>>
}