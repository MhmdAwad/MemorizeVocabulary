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
import com.mhmdawad.memorizevocabulary.presentation.new_vocabulary.AddVocabularyState
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
        private set
    var memorizeState by mutableStateOf(MemorizeScreenState())
        private set
    var randomVocabularyState by mutableStateOf(RandomVocabularyState())
        private set

    init {
        retrieveAllVocabularies()
    }

    private fun retrieveAllVocabularies() {
        memorizeState = memorizeState.copy(isLoading = true)
        memorizeRepository.retrieveAllVocabularies()
            .onEach {
                memorizeState = if (it.isNotEmpty()) {
                    memorizeState.copy(vocabularies = it, isNoVocabularies = false)
                } else {
                    memorizeState.copy(isNoVocabularies = true)
                }
                getRandomVocabulary()
            }.launchIn(viewModelScope)
    }

    fun getRandomVocabulary(delayTime: Long = 0L) {
        if (memorizeState.isNoVocabularies) return
        viewModelScope.launch {
            delay(delayTime)
            memorizeState = memorizeState.copy(isLoading = false)
            randomVocabularyState = RandomVocabularyState(memorizeState.vocabularies.random())
        }
    }

    fun insertVocabulary(englishVocabulary: String, nativeVocabulary: String) {
        if(englishVocabulary.isBlank() || nativeVocabulary.isBlank()){
            addVocabularyState = AddVocabularyState(errorMessage = "Please fill in the blanks first.")
            return
        }
        addVocabularyState = addVocabularyState.copy(isLoading = true)
        viewModelScope.launch {
            val result = VocabularyModule(
                englishVocabulary,
                nativeVocabulary
            ).let {
                memorizeRepository.insertNewVocabulary(it)
            }
            addVocabularyState = if (result == Constants.VOCABULARY_NOT_INSERTED) {
                // Not inserted
                AddVocabularyState(errorMessage = "An error occurred. Please try again!")
            } else {
                // Inserted successfully
                AddVocabularyState(isInsertedSuccessfully = true)
            }
        }
    }
}