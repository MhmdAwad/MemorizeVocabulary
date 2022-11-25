package com.mhmdawad.memorizevocabulary.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mhmdawad.memorizevocabulary.common.Constants
import com.mhmdawad.memorizevocabulary.data.database.MemorizeDao
import com.mhmdawad.memorizevocabulary.data.database.MemorizeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMemorizeDao(@ApplicationContext context: Context): MemorizeDao {
        return Room.databaseBuilder(
            context,
            MemorizeDatabase::class.java,
            Constants.DATABASE_NAME)
            .build()
            .getMemorizeDao()
    }
}