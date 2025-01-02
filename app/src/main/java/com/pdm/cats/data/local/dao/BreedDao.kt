package com.pdm.cats.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pdm.cats.data.local.entity.BreedEntity
import com.pdm.cats.data.local.entity.CatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreed(breed: BreedEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreeds(breeds: List<BreedEntity>)

    @Query("SELECT * FROM breed")
    fun getAllBreads(): Flow<List<BreedEntity>>
}