package com.raywenderlich.ticky.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raywenderlich.ticky.Taskie
import com.raywenderlich.ticky.db.TaskieDatabase
import kotlinx.coroutines.*


class TaskViewModel(private val repository: TaskieRepository ): ViewModel() {



     fun getTaskList() : LiveData<List<Taskie>> {
         return repository.getData()
     }


    fun addTask(task: Taskie) {
            repository.addTask(task)
    }

    fun getRowCount() {
        return repository.getRowCount()
    }

    fun deleteUser(task: List<Taskie>){
        viewModelScope.launch ( Dispatchers.IO){
            repository.deleteUser(task)
        }
    }
    fun deleteAllUser(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUser()
        }
    }

}