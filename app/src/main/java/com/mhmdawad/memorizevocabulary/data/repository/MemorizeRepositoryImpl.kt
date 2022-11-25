package com.mhmdawad.memorizevocabulary.data.repository

import com.mhmdawad.memorizevocabulary.data.database.MemorizeDao
import com.mhmdawad.memorizevocabulary.domain.module.VocabularyModule
import com.mhmdawad.memorizevocabulary.domain.repository.MemorizeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MemorizeRepositoryImpl @Inject constructor(
    private val memorizeDao: MemorizeDao
): MemorizeRepository {

    override suspend fun insertNewVocabulary(vocabularyModule: VocabularyModule) =
        memorizeDao.insertNewVocabulary(vocabularyModule)

    override suspend fun deleteSpecificVocabulary(vocabulary: String) =
        memorizeDao.deleteSpecificVocabulary(vocabulary)

    override suspend fun deleteAllVocabularies() =
        memorizeDao.deleteAllVocabularies()

    override fun retrieveAllVocabularies(): Flow<List<VocabularyModule>> =
        memorizeDao.retrieveAllVocabularies()

}