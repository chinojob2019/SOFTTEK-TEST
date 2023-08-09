package com.culqitest.softtek_test

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.culqitest.softtek_test.api.FilmService
import com.culqitest.softtek_test.data.FilmsRepository
import com.culqitest.softtek_test.room.RepoDatabase
import com.culqitest.softtek_test.ui.ViewModelFactory


object Injection {


    private fun provideGithubRepository(context: Context): FilmsRepository {
        return FilmsRepository(FilmService.create(), RepoDatabase.getInstance(context))
    }

    fun provideViewModelFactory(context: Context, owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
        return ViewModelFactory(owner, provideGithubRepository(context))
    }


}