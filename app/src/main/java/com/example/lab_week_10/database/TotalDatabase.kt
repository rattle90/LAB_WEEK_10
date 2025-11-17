package com.example.lab_week_10.database

import androidx.room.Database
import androidx.room.RoomDatabase

// @Database, sebutkan semua 'entities' dan 'version'
@Database(entities = [Total::class], version = 1)
abstract class TotalDatabase : RoomDatabase() {
    // Deklarasikan DAO yang dimiliki database ini
    abstract fun totalDao(): TotalDao
}