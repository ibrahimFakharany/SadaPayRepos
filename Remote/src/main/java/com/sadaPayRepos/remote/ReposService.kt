package com.sadaPayRepos.remote

import com.sadaPayRepos.model.GithubReposResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ReposService {
    @GET("repositories?q=language=+sort:stars")
    suspend fun getTrendingRepos(
        @Query("page") page: Int? = 1
    ): GithubReposResponse

}