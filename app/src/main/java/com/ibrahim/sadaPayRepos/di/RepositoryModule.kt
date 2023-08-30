package com.ibrahim.sadaPayRepos.di

import com.sadaPayRepos.data.ILocalDataSource
import com.sadaPayRepos.data.IRemoteDataSource
import com.sadaPayRepos.data.GithubReposRepository
import com.sadaPayRepos.domain.IGithubReposRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun providePostsRepository(
        remoteDataSource: IRemoteDataSource,
        localDataSource: ILocalDataSource,
    ): IGithubReposRepository = GithubReposRepository(remoteDataSource, localDataSource)

}