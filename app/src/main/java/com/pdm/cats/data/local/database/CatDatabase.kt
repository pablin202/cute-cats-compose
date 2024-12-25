package com.pdm.cats.data.local.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.pdm.cats.data.local.dao.CatDao
import com.pdm.cats.data.local.entity.CatEntity

@Database(
    entities = [CatEntity::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class CatDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
}