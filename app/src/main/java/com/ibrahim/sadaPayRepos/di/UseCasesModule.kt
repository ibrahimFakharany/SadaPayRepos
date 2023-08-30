package com.ibrahim.sadaPayRepos.di

import com.sadaPayRepos.domain.GetTrendingRepositoriesUseCase
import com.sadaPayRepos.domain.IGithubReposRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {
    @Provides
    fun provideGetTrendingReposCase(
        moviesRepository: IGithubReposRepository,
    ): GetTrendingRepositoriesUseCase = GetTrendingRepositoriesUseCase(moviesRepository)
}