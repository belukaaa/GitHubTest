package com.raywenderlich.ticky.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
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
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TaskAddingFragment: Fragment() , DatePickerDialog.OnDateSetListener {

    var TASK_COLOR : String = ""
    var TASK_DATE : String = ""
    var TASK_DATE1 : Long? = 999999999999999999
    var TASK_COLORED : Int = 10
    private var CIRCLE_POSITION1 : Boolean = false
    private var CIRCLE_POSITION2 : Boolean = false
    private var CIRCLE_POSITION3 : Boolean = false
    private var CIRCLE_POSITION4 : Boolean = false
    private var CIRCLE_POSITION5 : Boolean = false
    private var CIRCLE_POSITION6 : Boolean = false
    private var CIRCLE_POSITION7 : Boolean = false
    private var CIRCLE_POSITION8 : Boolean = false
    private var CIRCLE_POSITION9 : Boolean = false



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

        disableEnable()

        view.calendar.setOnClickListener{
            getDate()

        }

        setColor()

        view.saveButton.setOnClickListener {
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

        view.xoo.setOnClickListener {
            TASK_DATE = ""
            xoo.visibility = INVISIBLE
            calendar.visibility = VISIBLE
            dateCalendar.text = ""

        }


    }

    private fun disableEnable() {

        view?.Task_input?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                saveButton.setBackgroundResource(R.drawable.onboarding_button)

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (Task_input.text.toString().trim( {it <= ' '}).isEmpty()){
                    saveButton.setEnabled(false)
                    saveButton.setBackgroundResource(R.drawable.onboarding_button)

                }else {

                    saveButton.setEnabled(true)
                    saveButton.setBackgroundResource(R.drawable.disabled_save_button)
                }


            }

            override fun afterTextChanged(s: Editable?) {
              //  saveButton.setBackgroundResource(R.drawable.disabled_save_button)

            }

        })


    }
    
    private fun setColor()  {
        view?.oval1?.setOnClickListener {
            TASK_COLOR = "#ff453a"
            TASK_COLORED = 0

            if (CIRCLE_POSITION1){

                CIRCLE_POSITION1=false
            }
            else{

                CIRCLE_POSITION1 = true
            }

        }
        view?.oval2?.setOnClickListener {
            TASK_COLOR = "#ff9f0c"
            TASK_COLORED = 1

            if (CIRCLE_POSITION2){

                CIRCLE_POSITION2=false
            }
            else{

                CIRCLE_POSITION2 = true
            }
        }
        view?.oval3?.setOnClickListener {
            TASK_COLOR = "#ffd50c"
            TASK_COLORED = 2

            if (CIRCLE_POSITION3){

                CIRCLE_POSITION3=false
            }
            else{

                CIRCLE_POSITION3 = true
            }
        }
        view?.oval4?.setOnClickListener {
            TASK_COLOR = "#32d74b"
            TASK_COLORED = 3

            if (CIRCLE_POSITION4){

                CIRCLE_POSITION4=false
            }
            else{

                CIRCLE_POSITION4 = true
            }
        }
        view?.oval5?.setOnClickListener {
            TASK_COLOR = "#64d2ff"
            TASK_COLORED = 4

            if (CIRCLE_POSITION5){

                CIRCLE_POSITION5=false
            }
            else{

                CIRCLE_POSITION5 = true
            }
        }
        view?.oval6?.setOnClickListener {
            TASK_COLOR = "#0984ff"
            TASK_COLORED = 5

            if(CIRCLE_POSITION6) {
                circleBlue.visibility = INVISIBLE
                CIRCLE_POSITION6 = false
            }
            else{
                circleBlue.visibility = VISIBLE
                CIRCLE_POSITION6 = true
            }

        }
        view?.oval7?.setOnClickListener {
            TASK_COLOR = "#5e5ce6"
            TASK_COLORED = 6

            if (CIRCLE_POSITION7){

                CIRCLE_POSITION7=false
            }
            else{

                CIRCLE_POSITION7 = true
            }
        }
        view?.oval8?.setOnClickListener {
            TASK_COLOR = "#bf5af2"
            TASK_COLORED = 7

            if (CIRCLE_POSITION8){

                CIRCLE_POSITION8=false
            }
            else{

                CIRCLE_POSITION8 = true
            }
        }
        view?.oval9?.setOnClickListener {
            TASK_COLOR = "#ff375f"
            TASK_COLORED = 8

            if (CIRCLE_POSITION9){

                CIRCLE_POSITION9=false
            }
            else{

                CIRCLE_POSITION9 = true
            }
        }

    }
    private fun getDate() {
        val calendar : Calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)


        val datePickerDialog =
            DatePickerDialog(requireContext(), this, year, month, day)
        datePickerDialog.show()
    }

    private fun setCircle() {
        view?.oval5?.setOnClickListener{

        }
    }


    private fun insertDataToDatabase()  {
        val title = Task_input.text.toString().trim()
        val color = TASK_COLOR
        val datetime = TASK_DATE
        val checked = false
        val selected = false
        val dateLong = TASK_DATE1
        val sortingColor = TASK_COLORED



        if(title.isNotEmpty()){
            val task = Taskie(0, title, color, datetime, checked, selected, dateLong, sortingColor)
            mTaskViewModel.addTask(task)
            TASK_DATE = ""
            TASK_COLOR = ""
            TASK_COLORED = 10
            TASK_DATE1 = 999999999999999999
            Task_input.text.clear()
            Toast.makeText(requireContext(), " ADDED", Toast.LENGTH_LONG).show()

        }
    }

    private var listener1 : Task_addingButton? = null
    interface Task_addingButton{
        fun taskAdd()
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
        val Calendar = Calendar.getInstance()
        Calendar[year, month] = dayOfMonth

//        val formatedDate = sdf.format(calendar.time)
        Log.e("TAG", "Format date is ${Calendar.time}")
        val givenDateString = Calendar.time.toString()
        val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
        try {
            val mDate = sdf.parse(givenDateString)
            val timeInMilliseconds = mDate.time
            TASK_DATE1 = timeInMilliseconds
            Log.e("TAG", "Format date is ${timeInMilliseconds}")
        } catch (e: ParseException) {
            e.printStackTrace()
        }




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

    private fun setColorToRound(color: String) {
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






