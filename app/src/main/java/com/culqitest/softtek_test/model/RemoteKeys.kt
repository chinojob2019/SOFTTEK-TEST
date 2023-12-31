package com.culqitest.softtek_test.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val repoId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)