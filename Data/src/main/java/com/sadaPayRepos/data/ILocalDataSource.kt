package com.sadaPayRepos.data

import androidx.paging.PagingSource
import com.sadaPayRepos.model.GithubRepo
import com.sadaPayRepos.model.GithubReposRemoteKeys

interface ILocalDataSource {
    fun getTrendingRepoPagingSource(): PagingSource<Int, GithubRepo>
    suspend fun getRemoteKeys(id: Long): GithubReposRemoteKeys
    suspend fun saveData(
        isRefresh: Boolean, githubRepos: List<GithubRepo>, keys: List<GithubReposRemoteKeys>
    )

    suspend fun clearAll()
}