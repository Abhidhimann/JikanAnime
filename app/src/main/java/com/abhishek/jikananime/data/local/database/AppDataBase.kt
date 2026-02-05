package com.abhishek.jikananime.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abhishek.jikananime.data.local.dao.AnimeDao
import com.abhishek.jikananime.data.local.entity.AnimeEntity

@Database(entities = [AnimeEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}