package com.sadaPayRepos.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubReposResponse(
    @SerialName("items") val items: List<GithubRepo>,
    @SerialName("total_count") val totalCount: Int
)