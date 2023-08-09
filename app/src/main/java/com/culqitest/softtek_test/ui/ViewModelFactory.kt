package com.culqitest.softtek_test.ui

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.culqitest.softtek_test.data.FilmsRepository
import com.culqitest.softtek_test.ui.viewmodels.SearchFilmsViewModel


class ViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val repository: FilmsRepository
) : AbstractSavedStateViewModelFactory(owner, null) {


    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {


        if (modelClass.isAssignableFrom(SearchFilmsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchFilmsViewModel(repository, handle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")




    }
}