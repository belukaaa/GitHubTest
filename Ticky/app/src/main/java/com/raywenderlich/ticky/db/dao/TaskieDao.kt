package com.raywenderlich.ticky.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.raywenderlich.ticky.Taskie


@Dao
interface TaskieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTask(task : Taskie)

    @Query("SELECT * FROM tasks ORDER BY taskId ASC ")
    fun readAllData(): LiveData<List<Taskie>>

    @Query("SELECT * FROM tasks ORDER BY title ASC")
    suspend fun getRowCount() : List<Taskie>

    @Delete
    suspend fun deleteUser(task: List<Taskie>)

    @Query("DELETE FROM tasks")
    suspend fun deleteAllUsers()



}