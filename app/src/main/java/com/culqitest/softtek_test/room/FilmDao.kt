package com.culqitest.softtek_test.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.culqitest.softtek_test.model.FilmModel

@Dao
interface FilmDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<FilmModel>)

    @Query(
        "SELECT * FROM films WHERE " +
                "originalTitle LIKE :queryString OR title LIKE :queryString "

    )
    fun filmsByName(queryString: String): PagingSource<Int, FilmModel>

    @Query("DELETE FROM films")
    suspend fun clearFilms()


}