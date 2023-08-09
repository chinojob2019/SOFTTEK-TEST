package com.culqitest.softtek_test.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.culqitest.softtek_test.api.FilmService
import com.culqitest.softtek_test.model.FilmModel
import com.culqitest.softtek_test.room.RepoDatabase

import kotlinx.coroutines.flow.Flow

class FilmsRepository (

    private val service: FilmService,
    private val database: RepoDatabase

){

    fun getSearchResultStream(query: String): Flow<PagingData<FilmModel>> {
        Log.d("GithubRepository", "New query: $query")

        // appending '%' so we can allow other characters to be before and after the query string
        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { database.reposDao().filmsByName(dbQuery) }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = FilmsRemoteMediator(
                query,
                service,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }



}
