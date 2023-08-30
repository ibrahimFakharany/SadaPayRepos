package com.ibrahim.sadaPayRepos.di

import android.content.Context
import com.sadaPayRepos.data.ILocalDataSource
import com.sadaPayRepos.local.GithubRepos
import com.sadaPayRepos.local.LocalDataSource
import com.sadaPayRepos.local.RemoteKeysDao
import com.sadaPayRepos.local.ReposDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {
    @Provides
    fun provideRemoteDataSource(
        postsDao: GithubRepos,
        keysDao: RemoteKeysDao,
        database: ReposDatabase
    ): ILocalDataSource = LocalDataSource(database, postsDao, keysDao)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ReposDatabase {
        return ReposDatabase.create(context)
    }

    @Provides
    fun provideReposDao(database: ReposDatabase): GithubRepos {
        return database.getReposDao()
    }

    @Provides
    fun provideKeysDao(database: ReposDatabase): RemoteKeysDao {
        return database.getKeysDao()
    }
}