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
import com.mhmdawad.memorizevocabulary.presentation.memorize_screen.SyncVocabulariesState
import com.mhmdawad.memorizevocabulary.presentation.new_vocabulary.AddVocabularyState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
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
    var syncVocabulariesState by mutableStateOf(SyncVocabulariesState())
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
                    memorizeState.copy(isNoVocabularies = true, isLoading = false)
                }
                println("??????????????? $memorizeState")
                getRandomVocabulary()
            }.launchIn(viewModelScope)
    }

    fun getRandomVocabulary() {
        if (memorizeState.isNoVocabularies) return
        viewModelScope.launch {
            memorizeState = memorizeState.copy(isLoading = false)
            randomVocabularyState = RandomVocabularyState(memorizeState.vocabularies.random())
        }
    }

    fun insertVocabulary(englishVocabulary: String, nativeVocabulary: String) {
        if (englishVocabulary.isBlank() || nativeVocabulary.isBlank()) {
            addVocabularyState =
                AddVocabularyState(errorMessage = "Please fill in the blanks first.")
            return
        }
        addVocabularyState = addVocabularyState.copy(isLoading = true)
        viewModelScope.launch {
            val result = VocabularyModule(
                englishVocabulary.trim(),
                nativeVocabulary.trim()
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

    fun syncFromSpreadSheetToDb() {
        syncVocabulariesState = SyncVocabulariesState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val vocabularies = mutableListOf<VocabularyModule>()
                val `is` =
                    InputStreamReader(URL("https://docs.google.com/spreadsheets/d/e/2PACX-1vRe1J4qTILSePNKYIpQfx---hoH2QwWzDsDhDNvGWiah-B74JIwQ5_41AW_CkFbsQ6rB98PV0J5uMuh/pubhtml?gid=1411305950&single=true").openStream())
                val reader = BufferedReader(`is`)
                reader.readLine()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    val lines = line?.split("<tr")
                    lines?.forEach {
                        if (it.contains("<td class=\"s0\">") && it.contains("<td class=\"s2\" dir=\"rtl\">")) {
                            val english =
                                it.substringAfter("<td class=\"s0\">").substringBefore("</td>")
                            val native = it.substringAfter("<td class=\"s2\" dir=\"rtl\">")
                                .substringBefore("</td>")
                            vocabularies.add(
                                VocabularyModule(
                                    englishVocabulary = english,
                                    nativeVocabulary = native
                                )
                            )
                        }
                    }
                }
                vocabularies.forEach {
                    insertVocabulary(
                        it.englishVocabulary.replace("&#39;", "'"),
                        it.nativeVocabulary.replace("&#39;","'")
                    )
                }
                syncVocabulariesState = SyncVocabulariesState(isSyncedSuccessfully = true)
                delay(1000L)
                syncVocabulariesState = SyncVocabulariesState()
            } catch (e: Exception) {
                syncVocabulariesState = SyncVocabulariesState(errorMessage = "Check your internet!")
            }
        }
    }
}
