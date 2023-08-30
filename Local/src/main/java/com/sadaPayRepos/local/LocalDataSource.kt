package com.sadaPayRepos.local

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.sadaPayRepos.data.ILocalDataSource
import com.sadaPayRepos.model.GithubRepo
import com.sadaPayRepos.model.GithubReposRemoteKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    val database: ReposDatabase, val reposDao: GithubRepos, val keysDao: RemoteKeysDao
) : ILocalDataSource {

    override fun getTrendingRepoPagingSource(): PagingSource<Int, GithubRepo> {
        return reposDao.getTrendingRepos()
    }
    override suspend fun getRemoteKeys(id: Long): GithubReposRemoteKeys {
        return keysDao.getKeys(id)
    }


    override suspend fun saveData(
        isRefresh: Boolean, githubRepos: List<GithubRepo>, keys: List<GithubReposRemoteKeys>
    ) = database.withTransaction {
        if (isRefresh) {
            keysDao.clearAll()
            reposDao.clearAll()
        }
        keysDao.saveKeys(keys)
        reposDao.saveRepos(githubRepos)
    }

    override suspend fun clearAll() {
        withContext(Dispatchers.IO) {
            reposDao.clearAll()
            keysDao.clearAll()

        }
    }

}