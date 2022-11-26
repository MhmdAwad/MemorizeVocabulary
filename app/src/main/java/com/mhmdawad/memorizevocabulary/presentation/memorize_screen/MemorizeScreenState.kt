package com.mhmdawad.memorizevocabulary.presentation.memorize_screen

import com.mhmdawad.memorizevocabulary.domain.module.VocabularyModule

data class MemorizeScreenState(
    val vocabularies: List<VocabularyModule> = emptyList(),
    val isLoading: Boolean = false,
    val isNoVocabularies: Boolean = vocabularies.isEmpty()
)
