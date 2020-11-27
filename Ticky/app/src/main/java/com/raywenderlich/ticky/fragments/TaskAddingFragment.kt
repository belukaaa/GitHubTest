package com.raywenderlich.ticky.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.raywenderlich.ticky.R
import com.raywenderlich.ticky.Taskie
import com.raywenderlich.ticky.db.TaskieDatabase
import com.raywenderlich.ticky.db.dao.TaskieDao
import com.raywenderlich.ticky.repository.TaskViewModel
import com.raywenderlich.ticky.repository.TaskieRepository
import kotlinx.android.synthetic.main.adding_activity_task.*
import kotlinx.android.synthetic.main.adding_activity_task.view.*
import kotlinx.android.synthetic.main.todo_list_view_holder.*
import java.util.*



class TaskAddingFragment: Fragment() , DatePickerDialog.OnDateSetListener {
    //ქვემოთ გაქ გადმოტანილი თურამეა აატყვნეინე ფრაგმენტის ზემოთ
    var TASK_COLOR : String = ""
    var TASK_DATE : String = ""


    private lateinit var mTaskViewModel: TaskViewModel
    private lateinit var factory: com.raywenderlich.ticky.repository.Factory
    private lateinit var taskieDao: TaskieDao
    private lateinit var taskDB: TaskieDatabase
    private lateinit var repository: TaskieRepository


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.adding_activity_task, container, false)



        initViewModel()


        return view
    }

    private fun initViewModel() {

        taskDB = TaskieDatabase.getDatabase(requireContext())
        taskieDao = taskDB.taskieDao()
        repository = TaskieRepository(taskieDao)
        factory = com.raywenderlich.ticky.repository.Factory(repository)
        mTaskViewModel = ViewModelProviders.of(this, factory).get(TaskViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view.Task_input, InputMethodManager.SHOW_IMPLICIT)

        dateCalendar.visibility = INVISIBLE
        xoo.visibility = INVISIBLE


        view.calendar.setOnClickListener{
            getDate()

        }

        setColor()

        view.button2.setOnClickListener {
            insertDataToDatabase()
            listener1?.taskAdd()
        }
        view.back_to_tasks.setOnClickListener {
            listener?.bttnClicked()

        }

        view.textView4.setOnClickListener {
            TASK_COLOR = ""
            TASK_DATE = ""
            Task_input.text.clear()
            listener?.bttnClicked()

        }
        view.cancel_selecting2.setOnClickListener {
            TASK_COLOR = ""
            TASK_DATE = ""
            Task_input.text.clear()
            listener?.bttnClicked()
        }


    }
    
    private fun setColor() : String {
        view?.oval1?.setOnClickListener {
            TASK_COLOR = "#ff453a"
            println("yleqala $TASK_COLOR")
        }
        view?.oval2?.setOnClickListener {
            TASK_COLOR = "#ff9f0c"
            println("yleqala $TASK_COLOR")
        }
        view?.oval3?.setOnClickListener {
            TASK_COLOR = "#ffd50c"
            println("yleqala $TASK_COLOR")
        }
        view?.oval4?.setOnClickListener {
            TASK_COLOR = "#32d74b"
            println("yleqala $TASK_COLOR")
        }
        view?.oval5?.setOnClickListener {
            TASK_COLOR = "#64d2ff"
            println("yleqala $TASK_COLOR")
        }
        view?.oval6?.setOnClickListener {
            TASK_COLOR = "#0984ff"
            println("yleqala $TASK_COLOR")
        }
        view?.oval7?.setOnClickListener {
            TASK_COLOR = "#5e5ce6"
            println("yleqala $TASK_COLOR")
        }
        view?.oval8?.setOnClickListener {
            println("yleqala $TASK_COLOR")
            TASK_COLOR = "#bf5af2"
        }
        view?.oval9?.setOnClickListener {
            TASK_COLOR = "#ff375f"
            println("yleqala $TASK_COLOR")
        }

        return TASK_COLOR
    }


    private fun insertDataToDatabase()  {
        val title = Task_input.text.toString()
        val color = TASK_COLOR
        val dateTime = TASK_DATE
        val checked = false
        val selected = false


        if(title.isNotEmpty()){
            val task = Taskie(0 , title , color , dateTime , checked , selected)
            mTaskViewModel.addTask(task)
            TASK_DATE = ""
            TASK_COLOR = ""
            Task_input.text.clear()
            Toast.makeText(requireContext(), " ADDED" , Toast.LENGTH_LONG).show()

        }
    }

    private var listener1 : Task_addingButton? = null
    interface Task_addingButton{
        fun taskAdd()
    }


    private fun getDate() {
        val calendar : Calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)


        val datePickerDialog =
            DatePickerDialog(requireContext(), this ,year , month , day  )
        datePickerDialog.show()
    }



    companion object {
        fun getTaskFragInstance(): TaskAddingFragment{
            return TaskAddingFragment()
        }
    }

    var listener : BttnClicked? = null
    interface BttnClicked {
        fun bttnClicked()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as BttnClicked
        listener1 = context as Task_addingButton

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        listener1 = null

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        var myMonth = ""

        if(month == 0) {
            myMonth= ("Jan")
        }
        if(month == 1) {
            myMonth = ("Feb")
        }
        if(month == 2) {
            myMonth= ("Mar")
        }
        if(month == 3) {
            myMonth = ("Apr")
        }
        if(month == 4) {
            myMonth = ("May")
        }
        if(month == 5) {
            myMonth = ("Jun")
        }
        if(month == 6) {
            myMonth= ("Jul")
        }
        if(month == 7) {
            myMonth= ("Aug")
        }
        if(month == 8) {
            myMonth= ("Sep")
        }
        if(month == 9) {
            myMonth = ("Oct")
        }
        if(month == 10) {
            myMonth = ("Nov")
        }
        if(month == 11){
            myMonth = ("Dec")
        }
        TASK_DATE= "Due $dayOfMonth $myMonth,"
        if(dayOfMonth == 0 && myMonth.isEmpty()){
            calendar.visibility = VISIBLE
        }else{
            calendar.visibility = INVISIBLE
            xoo.visibility = VISIBLE
            dateCalendar.visibility = VISIBLE
            dateCalendar.text = TASK_DATE

        }



    }
    private fun setColorToRound(color : String) {
        if (color == "#ff453a"){
            task_color_red.visibility = VISIBLE
        }
        else if (color == "#ff9f0c"){
            task_color_orange.visibility = VISIBLE
        }
        else if (color == "#ffd50c"){
            task_color_yellow.visibility = VISIBLE
        }
        else if (color == "#32d74b"){
            task_color_green.visibility = VISIBLE
        }
        else if (color == "#64d2ff"){
            task_color_pachtiblue.visibility = VISIBLE
        }
        else if (color == "#0984ff"){
            task_color_blue.visibility = VISIBLE
        }
        else if (color == "#5e5ce6"){
            task_color_muqiblue.visibility = VISIBLE
        }
        else if (color == "#bf5af2"){
            task_color_purple.visibility = VISIBLE
        }
        else if (color == "#ff375f"){
            task_color_rose.visibility = VISIBLE
        }else{

        }

    }


}






