package com.sadaPayRepos.remote

import com.sadaPayRepos.data.IRemoteDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val service: ReposService) : IRemoteDataSource {
    override suspend fun fetchRepos(page: Int) = service.getTrendingRepos(page)
}