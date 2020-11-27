package com.raywenderlich.ticky.repository

import androidx.lifecycle.LiveData
import com.raywenderlich.ticky.Taskie
import com.raywenderlich.ticky.db.dao.TaskieDao
import kotlinx.coroutines.*

class TaskieRepository(private val taskDao: TaskieDao) {

    fun getData() : LiveData<List<Taskie>> {
       return taskDao.readAllData()
   }


     fun addTask(task : Taskie) {
       CoroutineScope(Dispatchers.IO).launch {
           taskDao.addTask(task)
       }
    }

    fun getRowCount() {
        CoroutineScope(Dispatchers.IO).launch {
             taskDao.getRowCount()
        }
    }

    suspend fun deleteUser(task: List<Taskie>){
        taskDao.deleteUser(task)
    }

    suspend fun deleteAllUser() {
        taskDao.deleteAllUsers()
    }




//    fun getCount(): Int = runBlocking {
//        val count = async {
//            taskDao.getCount()
//        }
//        count.start()
//        count.await()
//    }


}