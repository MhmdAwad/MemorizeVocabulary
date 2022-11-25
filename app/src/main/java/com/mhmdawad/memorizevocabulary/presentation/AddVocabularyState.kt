package com.mhmdawad.memorizevocabulary.presentation

import com.mhmdawad.memorizevocabulary.domain.module.VocabularyModule

data class AddVocabularyState(
    val isLoading: Boolean = false,
    val isInsertedSuccessfully: Boolean = false,
    val errorMessage: String = ""
)
