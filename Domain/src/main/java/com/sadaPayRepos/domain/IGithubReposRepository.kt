package com.sadaPayRepos.domain

import androidx.paging.PagingData
import com.sadaPayRepos.model.GithubRepo
import kotlinx.coroutines.flow.Flow

interface IGithubReposRepository {
    fun getRepos(): Flow<PagingData<GithubRepo>>

}