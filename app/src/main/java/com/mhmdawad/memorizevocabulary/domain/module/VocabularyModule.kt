package com.mhmdawad.memorizevocabulary.domain.module

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vocabularyModule")
data class VocabularyModule(
    @PrimaryKey
    val englishVocabulary: String = "",
    val nativeVocabulary: String = "",
)
