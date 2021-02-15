package com.raywenderlich.ticky

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.raywenderlich.ticky.db.TaskieDatabase
import com.raywenderlich.ticky.db.dao.TaskieDao
import com.raywenderlich.ticky.fragments.FirstScreenFragment
import com.raywenderlich.ticky.fragments.HomeTaskScreenFragment
import com.raywenderlich.ticky.fragments.OnboardingFragment
import com.raywenderlich.ticky.fragments.TaskAddingFragment
import com.raywenderlich.ticky.repository.Factory
import com.raywenderlich.ticky.repository.TaskViewModel
import com.raywenderlich.ticky.repository.TaskieRepository
import com.raywenderlich.ticky.taskrecyclerview.TodoListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity(), OnboardingFragment.ButtonClicked,
    FirstScreenFragment.Click, TaskAddingFragment.BttnClicked, TaskAddingFragment.Task_addingButton,
    HomeTaskScreenFragment.HomeTaskScreenButton {


    private lateinit var factory: Factory
    private lateinit var mTaskViewModel: TaskViewModel
    private lateinit var taskieDao: TaskieDao
    private lateinit var taskDB: TaskieDatabase
    private lateinit var homeTaskScreenFragment: HomeTaskScreenFragment
    private lateinit var onboardingFrag: OnboardingFragment
    private lateinit var FirstScreenFrag: FirstScreenFragment
    private lateinit var mySharedPref: MySharedPreference
    private lateinit var addTaskFrag: TaskAddingFragment
    private lateinit var repository: TaskieRepository
    private lateinit var adapter: TodoListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        onboardingFrag = OnboardingFragment.getFirstFragInstance()
        FirstScreenFrag = FirstScreenFragment.getFirstScreenFragInstance()
        mySharedPref = MySharedPreference(this)
        addTaskFrag = TaskAddingFragment.getTaskFragInstance()
        homeTaskScreenFragment = HomeTaskScreenFragment.getHomeTaskScrenFragment()
        adapter = TodoListAdapter()
        initViewModel(this)

        startingApp()


    }

    private fun initViewModel(context: Context) {

        taskDB = TaskieDatabase.getDatabase(context)
        taskieDao = taskDB.taskieDao()
        repository = TaskieRepository(taskieDao)
        factory = Factory(repository)
        mTaskViewModel = ViewModelProviders.of(this, factory).get(TaskViewModel::class.java)
        observe()

    }

    private fun observe(){
        mTaskViewModel.colorLIveData.observe(this, Observer {
            if (it.isEmpty()){
                firstScreen()
            }
        })
    }

    private fun startingApp() {
        if (mySharedPref.getWhenAplicationFirstOpened()) {
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Main) {


                }

            }

        }
        else {
            onboarrding()
        }
    }

    private fun onboarrding() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_id, onboardingFrag)
            .commit()
        mySharedPref.saveWhenAplicationFirstOpened(true)
    }

    private fun firstScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_id, FirstScreenFrag)
            .commit()
    }

    private fun homeScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_id, homeTaskScreenFragment)
            .commit()
    }

    override fun buttonClicked() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_id, FirstScreenFrag)
            .commit()
    }

    override fun Clicked() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_id, addTaskFrag)
            .commit()
    }

    override fun bttnClicked() {
        startingApp()
    }

    override fun taskAdd() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_id, homeTaskScreenFragment)
            .commit()
    }

    override fun homeTaskScrenButton() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_id, addTaskFrag)
            .commit()
    }

//    override fun getDate() {
//       val  calendar : Calendar = Calendar.getInstance()
//         day = calendar.get(Calendar.DAY_OF_MONTH)
//         month = calendar.get(Calendar.MONTH)
//         year = calendar.get(Calendar.YEAR)
//
//        val datePickerDialog =
//            DatePickerDialog(this, this ,year , month , day  )
//        datePickerDialog.show()
//
//    }
//
//    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
//        myDay = day
//        myYear = year
//        myMonth = month
//        calendar.textAlignment = myYear  + myMonth + myDay
//
//
//
//    }

}


//   val data = repository.getData()
//   if (data.hasObservers()) {
//  firstScreen()
//   }else {
//    homeScreen()
//   }
