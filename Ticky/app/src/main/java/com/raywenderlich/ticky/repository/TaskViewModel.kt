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
    fun getSelectedData() : LiveData<List<Taskie>>{
        return repository.getSelectedData()
    }
    fun getUnSelectedData() : LiveData<List<Taskie>> {
        return repository.getUnSelectedData()
    }
    fun getTasksByDate() : LiveData<List<Taskie>> {
        return repository.getTaskByDate()
    }
    fun getTasksByColor() : LiveData<List<Taskie>>{
        return  repository.getTasksByColor()
    }



    fun addTask(task: Taskie) {
            repository.addTask(task)
    }

    fun updateTask(task: Taskie) {
        repository.updateTask(task)
    }

//    suspend fun getRowCountt() : ArrayList<Taskie> {
//        return repository.getRowCountt()
//    }





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