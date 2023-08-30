package com.sadaPayRepos.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repos_remote_keys")
data class GithubReposRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val nextPage: Int?
)