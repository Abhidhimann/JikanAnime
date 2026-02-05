package com.abhishek.jikananime.di

import android.content.Context
import androidx.room.Room
import com.abhishek.jikananime.data.local.dao.AnimeDao
import com.abhishek.jikananime.data.local.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAnimeDao(database: AppDataBase): AnimeDao {
        return database.animeDao()
    }
}