package com.sadaPayRepos.domain

import androidx.paging.PagingData
import com.sadaPayRepos.model.GithubRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingRepositoriesUseCase @Inject constructor(private val githubReposRepository: IGithubReposRepository) {

    operator fun invoke(): Flow<PagingData<GithubRepo>> {
        return githubReposRepository.getRepos()
    }
}