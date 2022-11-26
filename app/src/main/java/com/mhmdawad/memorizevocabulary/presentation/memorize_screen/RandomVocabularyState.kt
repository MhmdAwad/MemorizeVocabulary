package com.mhmdawad.memorizevocabulary.presentation.memorize_screen

import com.mhmdawad.memorizevocabulary.domain.module.VocabularyModule

data class RandomVocabularyState(
    val vocabulary: VocabularyModule = VocabularyModule(),
    val showEnglishVocabulary: Boolean = true,
)
