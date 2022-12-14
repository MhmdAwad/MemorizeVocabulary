package com.mhmdawad.memorizevocabulary.presentation.memorize_screen

import androidx.core.widget.ContentLoadingProgressBar

data class SyncVocabulariesState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val isSyncedSuccessfully: Boolean = false
)
