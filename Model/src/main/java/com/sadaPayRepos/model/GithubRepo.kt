package com.sadaPayRepos.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "github_repos")
data class GithubRepo(
    @PrimaryKey(autoGenerate = true) val tableId: Int = 0,
    val id: Long,
    val name: String,
    val description: String,
    @Embedded val owner: Owner,
    @SerialName("stargazers_count") val starsCount: Long,
    @SerialName("language") val language: String?=null,
)