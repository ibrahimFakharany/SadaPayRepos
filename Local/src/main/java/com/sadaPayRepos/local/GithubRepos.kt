package com.sadaPayRepos.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.sadaPayRepos.model.GithubRepo

@Dao
interface GithubRepos {
    @Insert(onConflict = REPLACE)
    suspend fun saveRepos(githubRepos: List<GithubRepo>)

    @Query("SELECT * FROM github_repos")
    fun getTrendingRepos(): PagingSource<Int, GithubRepo>

    @Query("DELETE FROM github_repos")
    fun clearAll()
}