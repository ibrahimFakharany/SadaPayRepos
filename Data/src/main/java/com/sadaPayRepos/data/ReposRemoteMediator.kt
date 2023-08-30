package com.sadaPayRepos.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.sadaPayRepos.model.GithubRepo
import com.sadaPayRepos.model.GithubReposRemoteKeys

const val PER_PAGE = 30

@OptIn(ExperimentalPagingApi::class)
class ReposRemoteMediator constructor(
    private val remoteDataSource: IRemoteDataSource, private val localDataSource: ILocalDataSource
) : RemoteMediator<Int, GithubRepo>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GithubRepo>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1

                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPage
                }
            }

            val response = remoteDataSource.fetchRepos(page = currentPage)
            val endOfPaginationReached = currentPage * PER_PAGE >= response.totalCount
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            val keys = response.items.map { repo ->
                GithubReposRemoteKeys(
                    id = repo.id.toString(), nextPage = nextPage
                )
            }

            localDataSource.saveData(
                isRefresh = loadType == LoadType.REFRESH, keys = keys, githubRepos = response.items
            )

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, GithubRepo>
    ): GithubReposRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                localDataSource.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, GithubRepo>
    ): GithubReposRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            localDataSource.getRemoteKeys(id = repo.id)
        }
    }


}