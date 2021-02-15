package com.raywenderlich.ticky.fragments

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.ticky.MySharedPreference
import com.raywenderlich.ticky.R
import com.raywenderlich.ticky.Taskie
import com.raywenderlich.ticky.db.TaskieDatabase
import com.raywenderlich.ticky.db.dao.TaskieDao
import com.raywenderlich.ticky.repository.Factory
import com.raywenderlich.ticky.repository.TaskViewModel
import com.raywenderlich.ticky.repository.TaskieRepository
import com.raywenderlich.ticky.taskrecyclerview.SelectedTaskAdapter
import com.raywenderlich.ticky.taskrecyclerview.TodoListAdapter
import kotlinx.android.synthetic.main.dialog_fragment.*
import kotlinx.android.synthetic.main.dialog_fragment.view.*
import kotlinx.android.synthetic.main.home_task_screen.*
import kotlinx.android.synthetic.main.home_task_screen.view.*
import java.util.*
import kotlin.collections.ArrayList


class HomeTaskScreenFragment: Fragment()  , TodoListAdapter.IOnClick , TodoListAdapter.Iunselect , SelectedTaskAdapter.unSelectListener , TodoListAdapter.UpdateTask , SelectedTaskAdapter.updateTaskie   {

    private var list = ArrayList<Taskie>()
    private var checkedList = ArrayList<Taskie>()
    private var selectedList = ArrayList<Taskie>()
    private var sortBy : String = ""

    private lateinit var selectedTaskRecyclerView: RecyclerView
    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: TodoListAdapter
    private lateinit var selectedAdapter: SelectedTaskAdapter
    private lateinit var mTaskViewModel: TaskViewModel
    private lateinit var factory: Factory
    private lateinit var taskieDao: TaskieDao
    private lateinit var taskDB : TaskieDatabase
    private lateinit var repository: TaskieRepository
    private lateinit var mySharedPref : MySharedPreference


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_task_screen, container, false)

        initViewModel(view.context)



        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDatee()


        adapter = TodoListAdapter()
        adapter.setOnCheckListener(this)
        adapter.setCheckedTaskListener(this)
        adapter.updateTask(this)

        selectedAdapter = SelectedTaskAdapter()
        selectedAdapter.setOnCheckListener(this)
        selectedAdapter.setOnUpdateListener(this)

        selectedTaskRecyclerView = view.checked_recycler
        selectedTaskRecyclerView.adapter = selectedAdapter
        selectedTaskRecyclerView.layoutManager = LinearLayoutManager(context)

        recyclerView = view.recycle
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        mTaskViewModel.getSelectedData().observe(viewLifecycleOwner, Observer { user ->
            adapter.setData(user)
        })
        mTaskViewModel.getUnSelectedData().observe(viewLifecycleOwner, Observer { user ->
            selectedAdapter.setSelectedData(user)
        })


        add_task.setOnClickListener {
            listener?.homeTaskScrenButton()
        }



        delete_task_button.setOnClickListener {
            mTaskViewModel.deleteUser(list)
            list.forEach { task ->
                task.checked = false

            }
            hideDeleteDonebttns()
        }

        cancel_selecting.setOnClickListener {
            list.clear()
            mTaskViewModel.getSelectedData().observe(viewLifecycleOwner, Observer { user ->
                adapter.setData(user)
            })
            mTaskViewModel.getUnSelectedData().observe(viewLifecycleOwner, Observer { user ->
                selectedAdapter.setSelectedData(user)
            })
            hideDeleteDonebttns()
        }



        done_button.setOnClickListener {


            list.forEach { task ->
                task.selected = true
                task.checked = false
                mTaskViewModel.updateTask(task)
            }
            adapter.notifyDataSetChanged()

            hideDeleteDonebttns()
        }
        view.textView5.setOnClickListener {
            var data = showDialog()
        }
    }
    private fun showDialog(){
        val dialog = CustomDialogFragment()

        dialog.show(childFragmentManager , "CustomDialog")


    }
    private fun initViewModel(context: Context) {
        taskDB = TaskieDatabase.getDatabase(context)
        taskieDao = taskDB.taskieDao()
        repository = TaskieRepository(taskieDao)
        factory = Factory(repository)
        mTaskViewModel = ViewModelProviders.of(this, factory).get(TaskViewModel::class.java)
        mySharedPref = MySharedPreference(context)
    }
    companion object {
        fun getHomeTaskScrenFragment():HomeTaskScreenFragment {
            return  HomeTaskScreenFragment()
        }
    }
    private fun setDate() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)



        val day = c.get(Calendar.DAY_OF_WEEK)

        val dayOfTheMonth = c.get(Calendar.DAY_OF_MONTH)

         if(month == 0) {
            datetime1.text = ("January,$year")
        }
        else if(month == 1) {
            datetime1.text = ("February,$year")
        }
        else if(month == 2) {
            datetime1.text = ("March,$year")
        }
        else if(month == 3) {
            datetime1.text = ("April,$year")
        }
        else if(month == 4) {
            datetime1.text = ("May,$year")
        }
        else if(month == 5) {
            datetime1.text = ("June,$year")
        }
        else if(month == 6) {
            datetime1.text = ("July,$year")
        }
        else if(month == 7) {
            datetime1.text = ("August,$year")
        }
        else if(month == 8) {
            datetime1.text = ("September,$year")
        }
        else if(month == 9) {
            datetime1.text = ("October,$year")
        }
        else if(month == 10) {
            datetime1.text = ("November,$year")
        }
        else if(month == 11){
            datetime1.text = ("December,$year")
        }



        when (day) {
            1 -> {
                monday1.text = (dayOfTheMonth-6) .toString()
                tuesday1.text = (dayOfTheMonth - 5).toString()
                wednesday1.text = (dayOfTheMonth - 4).toString()
                thursday1.text = (dayOfTheMonth - 3).toString()
                friday1.text = (dayOfTheMonth - 2).toString()
                saturday1.text = (dayOfTheMonth -1).toString()
                sunday1.text = (dayOfTheMonth).toString()

            }
            2 -> {

                monday1.text = dayOfTheMonth.toString()
                tuesday1.text = (dayOfTheMonth + 1).toString()
                wednesday1.text = (dayOfTheMonth + 2).toString()
                thursday1.text = (dayOfTheMonth + 3).toString()
                friday1.text = (dayOfTheMonth + 4).toString()
                saturday1.text = (dayOfTheMonth + 5).toString()
                sunday1.text = (dayOfTheMonth + 6).toString()

            }
            3 -> {

                monday1.text = (dayOfTheMonth - 1).toString()
                tuesday1.text = (dayOfTheMonth).toString()
                wednesday1.text = (dayOfTheMonth + 1).toString()
                thursday1.text = (dayOfTheMonth + 2).toString()
                friday1.text = (dayOfTheMonth + 3).toString()
                saturday1.text = (dayOfTheMonth + 4).toString()
                sunday1.text = (dayOfTheMonth + 5).toString()

            }
            4 -> {

                monday1.text = (dayOfTheMonth - 2).toString()
                tuesday1.text = (dayOfTheMonth - 1).toString()
                wednesday1.text = (dayOfTheMonth).toString()
                thursday1.text = (dayOfTheMonth + 1).toString()
                friday1.text = (dayOfTheMonth + 2).toString()
                saturday1.text = (dayOfTheMonth + 3).toString()
                sunday1.text = (dayOfTheMonth + 4).toString()

            }
            5 -> {


                monday1.text = (dayOfTheMonth - 3).toString()
                tuesday1.text = (dayOfTheMonth - 2).toString()
                wednesday1.text = (dayOfTheMonth  -1).toString()
                thursday1.text = (dayOfTheMonth).toString()
                friday1.text = (dayOfTheMonth + 1).toString()
                saturday1.text = (dayOfTheMonth + 2).toString()
                sunday1.text = (dayOfTheMonth+ 3 ).toString()

            }
            6 -> {


                monday1.text = (dayOfTheMonth - 4).toString()
                tuesday1.text = (dayOfTheMonth - 3).toString()
                wednesday1.text = (dayOfTheMonth -2).toString()
                thursday1.text = (dayOfTheMonth-1).toString()
                friday1.text = (dayOfTheMonth ).toString()
                saturday1.text = (dayOfTheMonth + 1).toString()
                sunday1.text = (dayOfTheMonth+ 2 ).toString()

            }
            else -> {

                monday1.text = (dayOfTheMonth - 5).toString()
                tuesday1.text = (dayOfTheMonth - 4).toString()
                wednesday1.text = (dayOfTheMonth - 3).toString()
                thursday1.text = (dayOfTheMonth - 2).toString()
                friday1.text = (dayOfTheMonth -1).toString()
                saturday1.text = (dayOfTheMonth ).toString()
                sunday1.text = (dayOfTheMonth + 1).toString()

            }
        }

    }

    private fun setDatee(){
        val c = Calendar.getInstance()
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val dayOfWeek = c.get(Calendar.DAY_OF_WEEK)
        val dayOfMonth = c.get(Calendar.DAY_OF_MONTH)



        monday1.text = (dayOfMonth - 2).toString()
        tuesday1.text = (dayOfMonth - 1).toString()
        wednesday1.text = dayOfMonth.toString()
        thursday1.text = (dayOfMonth + 1) . toString()
        friday1.text = (dayOfMonth + 2).toString()
        saturday1.text = (dayOfMonth + 3).toString()
        sunday1.text = (dayOfMonth + 4).toString()

        if (dayOfMonth == 1 && month == 0  )  {
            monday1.text = "30"
            tuesday1.text = "31"
        }
        else if (dayOfMonth == 1 && month == 2  )  {
            monday1.text = "30"
            tuesday1.text = "31"
        }

        else if (dayOfMonth == 1 && month == 4  )  {
            monday1.text = "30"
            tuesday1.text = "31"
        }

        else if (dayOfMonth == 1 && month == 6  )  {
            monday1.text = "30"
            tuesday1.text = "31"
        }

        else if (dayOfMonth == 1 && month == 7  )  {
            monday1.text = "30"
            tuesday1.text = "31"
        }

        else if (dayOfMonth == 1 && month == 9  )  {
            monday1.text = "30"
            tuesday1.text = "31"
        }

        else if (dayOfMonth == 1 && month == 11 )  {
            monday1.text = "30"
            tuesday1.text = "31"
        }
        else if (dayOfMonth == 2 && month == 0  )  {
            monday1.text = "31"
            tuesday1.text = "1"
        }
        else if (dayOfMonth == 2 && month == 2  )  {
            monday1.text = "31"
            tuesday1.text = "1"
        }

        else if (dayOfMonth == 2 && month == 4  )  {
            monday1.text = "31"
            tuesday1.text = "1"
        }

        else if (dayOfMonth == 2 && month == 6  )  {
            monday1.text = "31"
            tuesday1.text = "1"
        }

        else if (dayOfMonth == 2 && month == 7  )  {
            monday1.text = "31"
            tuesday1.text = "1"
        }

        else if (dayOfMonth == 2 && month == 9  )  {
            monday1.text = "31"
            tuesday1.text = "1"
        }

        else if (dayOfMonth == 2 && month == 11  )  {
            monday1.text = "31"
            tuesday1.text = "1"
        }
        else if (dayOfMonth == 1 && month == 1){
            tuesday1.text = "28"
            monday1.text = "27"
        }
        else if (dayOfMonth == 2 && month == 1){
            tuesday1.text = "1"
            monday1.text = "28"
        }
        else if (dayOfMonth == 1 && month == 3){
            tuesday1.text = "30"
            monday1.text = "29"
        }
        else if (dayOfMonth == 1 && month == 5){
            tuesday1.text = "30"
            monday1.text = "29"
        }
        else if (dayOfMonth == 1 && month == 8){
            tuesday1.text = "30"
            monday1.text = "29"
        }
        else if (dayOfMonth == 1 && month == 10){
            tuesday1.text = "30"
            monday1.text = "29"
        }
        else if (dayOfMonth == 2 && month == 3){
            tuesday1.text = "1"
            monday1.text = "30"
        }
        else if (dayOfMonth == 2 && month == 5){
            tuesday1.text = "1"
            monday1.text = "30"
        }
        else if (dayOfMonth == 2 && month == 8){
            tuesday1.text = "1"
            monday1.text = "30"
        }
        else if (dayOfMonth == 2 && month == 10){
            tuesday1.text = "1"
            monday1.text = "30"
        }
        else if (dayOfMonth == 28 && month == 0) {
            sunday1.text = "1"
        }

        else if (dayOfMonth == 28 && month == 2 ) {
            sunday1.text = "1"
        }

        else if (dayOfMonth == 28 && month == 4) {
            sunday1.text = "1"
        }

        else if (dayOfMonth == 28 && month == 6) {
            sunday1.text = "1"
        }

        else if (dayOfMonth == 28 && month == 7) {
            sunday1.text = "1"
        }

        else if (dayOfMonth == 28 && month == 9) {
            sunday1.text = "1"
        }

        else if (dayOfMonth == 28 && month == 11) {
            sunday1.text = "1"
        }

        else if (dayOfMonth == 29 && month == 0) {
            sunday1.text = "2"
            saturday1.text = "1"
        }
        else if (dayOfMonth == 29 && month == 2) {
            sunday1.text = "2"
            saturday1.text = "1"
        }
        else if (dayOfMonth == 29 && month == 4) {
            sunday1.text = "2"
            saturday1.text = "1"
        }
        else if (dayOfMonth == 29 && month == 6) {
            sunday1.text = "2"
            saturday1.text = "1"
        }
        else if (dayOfMonth == 29 && month == 7) {
            sunday1.text = "2"
            saturday1.text = "1"
        }
        else if (dayOfMonth == 29 && month == 9) {
            sunday1.text = "2"
            saturday1.text = "1"
        }
        else if (dayOfMonth == 29 && month == 11) {
            sunday1.text = "2"
            saturday1.text = "1"
        }
        else if (dayOfMonth == 30 && month == 0) {

            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }

        else if (dayOfMonth == 30 && month == 2) {

            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }

        else if (dayOfMonth == 30 && month == 4) {

            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }

        else if (dayOfMonth == 30 && month == 6) {

            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }

        else if (dayOfMonth == 30 && month == 7) {

            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }

        else if (dayOfMonth == 30 && month == 9) {

            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }

        else if (dayOfMonth == 30 && month == 11) {

            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }

        else if (dayOfMonth == 31 && month == 0) {

            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }
        else if (dayOfMonth == 31 && month == 2) {

            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }
        else if (dayOfMonth == 31 && month == 4) {

            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }
        else if (dayOfMonth == 31 && month == 6) {

            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }
        else if (dayOfMonth == 31 && month == 7) {

            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }
        else if (dayOfMonth == 31 && month == 9) {

            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }
        else if (dayOfMonth == 31 && month == 11) {

            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }
        else if (dayOfMonth == 27 && month == 3) {
            sunday1.text = "1"
        }

        else if (dayOfMonth == 27 && month == 5) {
            sunday1.text = "1"
        }

        else if (dayOfMonth == 27 && month == 8) {
            sunday1.text = "1"
        }

        else if (dayOfMonth == 27 && month == 10) {
            sunday1.text = "1"
        }
        else if (dayOfMonth == 28 && month == 3) {
            sunday1.text = "2"
            saturday1.text = "1"
        }

        else if (dayOfMonth == 28 && month == 5) {
            sunday1.text = "2"
            saturday1.text = "1"
        }

        else if (dayOfMonth == 28 && month == 8) {
            sunday1.text = "2"
            saturday1.text = "1"
        }

        else if (dayOfMonth == 28 && month == 10) {
            sunday1.text = "2"
            saturday1.text = "1"
        }
        else if (dayOfMonth == 29 && month == 3) {
            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }
        else if (dayOfMonth == 29 && month == 5) {
            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }
        else if (dayOfMonth == 29 && month == 8) {
            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }
        else if (dayOfMonth == 29 && month == 10) {
            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }
        else if (dayOfMonth == 30 && month == 3) {
            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }

        else if (dayOfMonth == 30 && month == 5) {
            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }

        else if (dayOfMonth == 30 && month == 8) {
            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }

        else if (dayOfMonth == 30 && month == 10) {
            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }
        else if(dayOfMonth == 28 && month == 1){
            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }


        if (dayOfWeek == 0) {
                Monday1.text = "T"
                monday21.text = "F"
                monday41.text = "S"
                monday51.text = "S"
                monday61.text = "M"
                monday71.text = "T"
                monday81.text = "W"

            } else if (dayOfWeek == 1) {
                Monday1.text = "F"
                monday21.text = "S"
                monday41.text = "S"
                monday51.text = "M"
                monday61.text = "T"
                monday71.text = "W"
                monday81.text = "T"

            } else if (dayOfWeek == 2) {
                Monday1.text = "S"
                monday21.text = "S"
                monday41.text = "M"
                monday51.text = "T"
                monday61.text = "W"
                monday71.text = "T"
                monday81.text = "F"
            } else if (dayOfWeek == 3) {
                Monday1.text = "S"
                monday21.text = "M"
                monday41.text = "T"
                monday51.text = "W"
                monday61.text = "T"
                monday71.text = "F"
                monday81.text = "S"
            } else if (dayOfWeek == 4) {
                Monday1.text = "M"
                monday21.text = "T"
                monday41.text = "W"
                monday51.text = "T"
                monday61.text = "F"
                monday71.text = "S"
                monday81.text = "S"

            } else if (dayOfWeek == 5) {
                Monday1.text = "T"
                monday21.text = "W"
                monday41.text = "T"
                monday51.text = "F"
                monday61.text = "S"
                monday71.text = "S"
                monday81.text = "M"
            } else if (dayOfWeek == 6) {
                Monday1.text = "W"
                monday21.text = "T"
                monday41.text = "F"
                monday51.text = "S"
                monday61.text = "S"
                monday71.text = "M"
                monday81.text = "T"

            }

            if (month == 0) {
                datetime1.text = ("January,$year")
            }
            if (month == 1) {
                datetime1.text = ("February,$year")
            }
            if (month == 2) {
                datetime1.text = ("March,$year")
            }
            if (month == 3) {
                datetime1.text = ("April,$year")
            }
            if (month == 4) {
                datetime1.text = ("May,$year")
            }
            if (month == 5) {
                datetime1.text = ("June,$year")
            }
            if (month == 6) {
                datetime1.text = ("July,$year")
            }
            if (month == 7) {
                datetime1.text = ("August,$year")
            }
            if (month == 8) {
                datetime1.text = ("September,$year")
            }
            if (month == 9) {
                datetime1.text = ("October,$year")
            }
            if (month == 10) {
                datetime1.text = ("November,$year")
            }
            if (month == 11) {
                datetime1.text = ("December,$year")
            }






    }


    private var listener : HomeTaskScreenButton? =null

  //  private var listener1 : UnselectAll ? = null

    interface HomeTaskScreenButton {
        fun homeTaskScrenButton()
    }
//    interface UnselectAll {
//        fun UnselectAllTasks()
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as HomeTaskScreenButton
//        listener1 = context as UnselectAll
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    //    listener1 = null

    }
    private fun hideDeleteDonebttns(){
        selected_item_funcs.visibility =View.INVISIBLE
    }
    private fun showDeleteDonebtttns() {
        selected_item_funcs.visibility = View.VISIBLE

    }
    override fun onChecked(list : List<Taskie>) {
        this.list = list as ArrayList<Taskie>


        if (list.isEmpty()) {
            hideDeleteDonebttns()
        } else {
            showDeleteDonebtttns()
        }

    }



    override fun unSelect(list: List<Taskie>) {

        this.checkedList = list as ArrayList<Taskie>

        selectedAdapter.setSelectedData(checkedList)


    }

    override fun unSelectSelected(list: List<Taskie>) {

        this.selectedList = list as ArrayList<Taskie>



    }

    override fun updateTask(task : Taskie) {
        mTaskViewModel.updateTask(task)
    }

    override fun updateTaskie(task: Taskie) {
        mTaskViewModel.updateTask(task)
    }

   fun sortBy(sort: String) {
        this.sortBy = sort

        Toast.makeText(context, sort, Toast.LENGTH_SHORT).show()

       if (sortBy == "Date added") {
           mTaskViewModel.getSelectedData().observe(viewLifecycleOwner , Observer {
               adapter.setData(it)

           })
       }
        else if (sortBy == "Due date"){
            mTaskViewModel.getTasksByDate().observe(viewLifecycleOwner , Observer {
                adapter.setData(it)
            })

       }
       else if (sortBy == "Color label") {

           mTaskViewModel.colorLIveData.observe(viewLifecycleOwner , Observer{
               adapter.setData(it)
           })
       }


    }



}