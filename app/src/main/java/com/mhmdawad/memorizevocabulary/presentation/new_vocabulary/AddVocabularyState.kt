package com.mhmdawad.memorizevocabulary.presentation.new_vocabulary

import com.mhmdawad.memorizevocabulary.domain.module.VocabularyModule

data class AddVocabularyState(
    val isLoading: Boolean = false,
    val isInsertedSuccessfully: Boolean = false,
    val errorMessage: String = ""
)
