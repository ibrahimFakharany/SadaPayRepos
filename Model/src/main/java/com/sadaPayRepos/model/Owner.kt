package com.sadaPayRepos.model

import androidx.room.ColumnInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Owner(
    val login: String,
    @ColumnInfo(name = "owner_id") val id: Long,
    @SerialName("avatar_url") val avatar: String
)