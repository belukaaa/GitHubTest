package com.raywenderlich.ticky.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.ticky.MySharedPreference
import com.raywenderlich.ticky.R
import com.raywenderlich.ticky.db.TaskieDatabase
import com.raywenderlich.ticky.db.dao.TaskieDao
import com.raywenderlich.ticky.repository.Factory
import com.raywenderlich.ticky.repository.TaskViewModel
import com.raywenderlich.ticky.repository.TaskieRepository
import com.raywenderlich.ticky.taskrecyclerview.SelectedTaskAdapter
import com.raywenderlich.ticky.taskrecyclerview.TodoListAdapter
import kotlinx.android.synthetic.main.dialog_fragment.*
import kotlinx.android.synthetic.main.dialog_fragment.view.*
import kotlinx.android.synthetic.main.home_task_screen.view.*

class CustomDialogFragment : DialogFragment() {

    private var SELECTED_ID = ""

    private lateinit var selectedTaskRecyclerView: RecyclerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TodoListAdapter
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
        var rootView: View = inflater.inflate(R.layout.dialog_fragment , container , false )

        dialog?.window?.setGravity(Gravity.BOTTOM)





        rootView.date_added.setOnClickListener {

            val selectedID = radio_group.checkedRadioButtonId
            val radio = rootView.findViewById<RadioButton>(selectedID)

            var result = radio.text.toString()
            SELECTED_ID = result


            Toast.makeText(context , "You selected $result" , Toast.LENGTH_LONG)
            Toast.makeText(context,"You selected $result" , Toast.LENGTH_LONG)
            dismiss()

        }

        rootView.due_date.setOnClickListener {
            val selectedID = radio_group.checkedRadioButtonId
            val radio = rootView.findViewById<RadioButton>(selectedID)

            var result = radio.text.toString()
            SELECTED_ID = result
            Toast.makeText(context,"You selected $result" , Toast.LENGTH_LONG)
            dismiss()


        }
        rootView.color_label.setOnClickListener{
            val selectedID = radio_group.checkedRadioButtonId
            val radio = rootView.findViewById<RadioButton>(selectedID)

            var result = radio.text.toString()
            SELECTED_ID = result
            Toast.makeText(context,"You selected $result" , Toast.LENGTH_LONG)
            dismiss()


        }

       /* if (SELECTED_ID == "Date added"){
            mTaskViewModel.getTaskList().observe(viewLifecycleOwner , Observer { user ->
                adapter.setData(user)
            })
            dismiss()
        }
        else if(SELECTED_ID == "Due date"){
            mTaskViewModel.getTasksByDate().observe(viewLifecycleOwner , Observer { user ->
                adapter.setData(user)
            })
            dismiss()
        }
        else if(SELECTED_ID == "Color label"){
            mTaskViewModel.getTasksByColor().observe(viewLifecycleOwner , Observer { user ->
                adapter.setData(user)
            })
            dismiss()
        }
*/

        return rootView
    }


}