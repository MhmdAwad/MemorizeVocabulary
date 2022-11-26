package com.mhmdawad.memorizevocabulary.di

import com.mhmdawad.memorizevocabulary.data.repository.MemorizeRepositoryImpl
import com.mhmdawad.memorizevocabulary.domain.repository.MemorizeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindMemorizeRepository(memorizeRepositoryImpl: MemorizeRepositoryImpl): MemorizeRepository
}