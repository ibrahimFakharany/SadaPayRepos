package com.sadaPayRepos.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.sadaPayRepos.model.GithubReposRemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = REPLACE)
    suspend fun saveKeys(keys: List<GithubReposRemoteKeys>)

    @Query("SELECT * FROM repos_remote_keys WHERE id=:id")
    suspend fun getKeys(id: Long): GithubReposRemoteKeys

    @Query("DELETE FROM repos_remote_keys")
    suspend fun clearAll()
}