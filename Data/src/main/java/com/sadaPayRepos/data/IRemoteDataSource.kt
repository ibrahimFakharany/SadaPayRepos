package com.sadaPayRepos.data

import com.sadaPayRepos.model.GithubReposResponse

interface IRemoteDataSource {
    suspend fun fetchRepos(page: Int): GithubReposResponse
}