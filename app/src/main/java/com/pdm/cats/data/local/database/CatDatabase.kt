package com.pdm.cats.data.local.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.pdm.cats.data.local.dao.BreedDao
import com.pdm.cats.data.local.dao.CatDao
import com.pdm.cats.data.local.entity.BreedEntity
import com.pdm.cats.data.local.entity.CatEntity

@Database(
    entities = [CatEntity::class, BreedEntity::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
    ]
)
abstract class CatDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
    abstract fun breedDao(): BreedDao
}