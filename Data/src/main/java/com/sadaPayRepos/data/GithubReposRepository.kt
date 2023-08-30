package com.sadaPayRepos.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sadaPayRepos.domain.IGithubReposRepository
import com.sadaPayRepos.model.GithubRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GithubReposRepository @Inject constructor(
    val remoteDataSource: IRemoteDataSource, val localDataSource: ILocalDataSource
) : IGithubReposRepository {

    override fun getRepos(): Flow<PagingData<GithubRepo>> {
        return Pager(config = PagingConfig(pageSize = PER_PAGE),
            remoteMediator = ReposRemoteMediator(remoteDataSource, localDataSource),
            pagingSourceFactory = {
                localDataSource.getTrendingRepoPagingSource()
            }).flow
    }

}