package com.example.lab_week_10.database

import androidx.room.*

// @Dao untuk Data Access Object
@Dao
interface TotalDao {
    // @Insert untuk menambah data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(total: Total)

    // @Update untuk mengubah data
    @Update
    fun update(total: Total)

    // @Delete untuk menghapus data
    @Delete
    fun delete(total: Total)

    // @Query untuk mengambil data
    @Query("SELECT * FROM total WHERE id = :id")
    fun getTotal(id: Long): List<Total>
}