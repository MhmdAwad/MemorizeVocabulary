package com.mhmdawad.memorizevocabulary.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmdawad.memorizevocabulary.common.Constants
import com.mhmdawad.memorizevocabulary.domain.module.VocabularyModule
import com.mhmdawad.memorizevocabulary.domain.repository.MemorizeRepository
import com.mhmdawad.memorizevocabulary.presentation.memorize_screen.MemorizeScreenState
import com.mhmdawad.memorizevocabulary.presentation.memorize_screen.RandomVocabularyState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemorizeViewModel @Inject constructor(
    private val memorizeRepository: MemorizeRepository,
) : ViewModel() {

    var addVocabularyState by mutableStateOf(AddVocabularyState())
    var memorizeState by mutableStateOf(MemorizeScreenState())
    var randomVocabularyState by mutableStateOf(RandomVocabularyState())

    init {
        retrieveAllVocabularies()
    }

    private fun retrieveAllVocabularies() {
        memorizeState = memorizeState.copy(isLoading = true)
        memorizeRepository.retrieveAllVocabularies()
            .onEach {
                memorizeState = if (it.isNotEmpty()) {
                    MemorizeScreenState(vocabularies = it)
                } else {
                    MemorizeScreenState(isNoVocabularies = true)
                }
                getRandomVocabulary()
            }.launchIn(viewModelScope)
    }

    fun getRandomVocabulary() {
        if (memorizeState.isNoVocabularies) return
        randomVocabularyState = RandomVocabularyState(memorizeState.vocabularies.random())
    }

    fun rotateVocabulary() {
        randomVocabularyState = randomVocabularyState.copy(
            showEnglishVocabulary = !randomVocabularyState.showEnglishVocabulary
        )
    }

    fun insertVocabulary(vocabularyModule: VocabularyModule) {
        addVocabularyState = addVocabularyState.copy(isLoading = true)
        viewModelScope.launch {
            delay(2000L)
            val result = memorizeRepository.insertNewVocabulary(vocabularyModule)
            addVocabularyState = if (result == Constants.VOCABULARY_NOT_INSERTED) {
                // Not inserted
                AddVocabularyState(errorMessage = "An error occurred!")
            } else {
                // Inserted successfully
                AddVocabularyState(isInsertedSuccessfully = true)
            }
        }
    }
}