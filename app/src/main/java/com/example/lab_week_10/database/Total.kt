package com.example.lab_week_10.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity mendefinisikan tabel dengan nama "total"
@Entity(tableName = "total")
data class Total(
    // @PrimaryKey sebagai kunci utama
    @PrimaryKey(autoGenerate = true)
    // @ColumnInfo untuk nama kolom
    @ColumnInfo(name = "id")
    val id: Long = 0,

    // Kolom kedua untuk menyimpan nilai total
    @ColumnInfo(name = "total")
    val total: Int = 0,
)