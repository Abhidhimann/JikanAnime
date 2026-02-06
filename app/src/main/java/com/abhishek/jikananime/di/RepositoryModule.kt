package com.abhishek.jikananime.di

import com.abhishek.jikananime.data.respository.AnimeRepositoryImpl
import com.abhishek.jikananime.domain.repository.AnimeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAnimeRepository(
        animeRepositoryImpl: AnimeRepositoryImpl,
    ): AnimeRepository
}