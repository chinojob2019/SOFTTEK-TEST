package com.culqitest.softtek_test.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.culqitest.softtek_test.api.FilmService
import com.culqitest.softtek_test.model.FilmModel
import com.culqitest.softtek_test.model.RemoteKeys
import com.culqitest.softtek_test.room.RepoDatabase
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class FilmsRemoteMediator(
    private  val query:String,
    private val service: FilmService,
    private val filmDatabase: RepoDatabase
) : RemoteMediator<Int, FilmModel>()

{
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FilmModel>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 0
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }



        try {
            val apiResponse = service.searchRepos(page)

            val repos = apiResponse.results
            val endOfPaginationReached = repos?.isEmpty()
            filmDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    filmDatabase.remoteKeysDao().clearRemoteKeys()
                    filmDatabase.reposDao().clearFilms()
                }
                val prevKey = if (page == 0) null else page - 1
                val nextKey = if (endOfPaginationReached == true) null else page + 1
                val keys = repos?.map {
                    RemoteKeys(repoId = it?.id!!, prevKey = prevKey, nextKey = nextKey)
                }

                if (keys != null) {
                    filmDatabase.remoteKeysDao().insertAll(keys)
                }
                filmDatabase.reposDao().insertAll(repos)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached!!)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, FilmModel>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                filmDatabase.remoteKeysDao().remoteKeysRepoId(repo.id!!)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, FilmModel>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                filmDatabase.remoteKeysDao().remoteKeysRepoId(repo.id!!)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, FilmModel>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                filmDatabase.remoteKeysDao().remoteKeysRepoId(repoId)
            }
        }
    }

}