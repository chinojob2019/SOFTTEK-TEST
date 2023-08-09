package com.culqitest.softtek_test.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmService {
    /**
     * Get repos ordered by stars.
     */
    @GET("3/movie/upcoming?page=1&api_key=f46b58478f489737ad5a4651a4b25079")
    suspend fun searchRepos(
        @Query("page") page: Int
    ): FilmsResponse

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/"

        fun create(): FilmService {
            val logger = HttpLoggingInterceptor()
            logger.level = Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FilmService::class.java)
        }
    }
}
