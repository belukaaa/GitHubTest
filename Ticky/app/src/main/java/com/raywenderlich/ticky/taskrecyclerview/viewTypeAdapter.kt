package com.raywenderlich.ticky.taskrecyclerview

import android.R.attr.data
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.ticky.R
import com.raywenderlich.ticky.Taskie
import kotlinx.android.synthetic.main.chechked_task_viewholder.view.*
import kotlinx.android.synthetic.main.todo_list_view_holder.view.*
import java.util.*
import kotlin.collections.ArrayList


class viewTypeAdapter(val click: (list: List<Taskie>, itemView: ArrayList<View>, position: ArrayList<Int>) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder> () {

    var completedlist = ArrayList<Taskie>()
    var chechkedList = ArrayList<Taskie>()
    var taskList1 = ArrayList<Taskie>()
    var TaskieView = ArrayList<View>()
    var taskListPosition = ArrayList<Int>()

    companion object {
        val VIEW_TYPE_ONE = 1
        val VIEW_TYPE_TWO = 2
        val VIEW_TYPE_HEADER_UNCOMPLITED = 3

    }
    inner class ViewHolderUncompletedTasks(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun select(task: Taskie, position: Int) {
            itemView.setOnLongClickListener {
                if (!task.checked) {
                    task.checked = true
                    itemView.linearLayout.setBackgroundResource(R.drawable.selected_item)
                    itemView.checkBox.setButtonDrawable(R.drawable.ic_rectangle_completed)
                    taskList1.add(task)
                    TaskieView.add(itemView)
                    taskListPosition.add(adapterPosition)
                    click.invoke(taskList1, TaskieView, taskListPosition)

                } else {
                    task.checked = false
                    itemView.checkBox.isChecked = false
                    itemView.linearLayout.setBackgroundResource(R.drawable.viewholder_background)
                    itemView.checkBox.setButtonDrawable(R.drawable.unselected_task_checkbox)
                    taskList1.remove(task)
                    TaskieView.remove(itemView)
                    taskListPosition.remove(adapterPosition)
                    click.invoke(taskList1, TaskieView, taskListPosition)
                }
                true
            }
        }


            var taskText: TextView = itemView.findViewById(R.id.task)
            var dateTime: TextView = itemView.findViewById(R.id.task_date)
            val redColor: TextView = itemView.findViewById(R.id.task_color_red)
            val blueColor: TextView = itemView.findViewById(R.id.task_color_blue)
            val orangeColor: TextView = itemView.findViewById(R.id.task_color_orange)
            val yellowColor: TextView = itemView.findViewById(R.id.task_color_yellow)
            val greenColor: TextView = itemView.findViewById(R.id.task_color_green)
            val mkrtaliBlueColor: TextView = itemView.findViewById(R.id.task_color_pachtiblue)
            val muqiBlueColor: TextView = itemView.findViewById(R.id.task_color_muqiblue)
            val purpleColor: TextView = itemView.findViewById(R.id.task_color_purple)
            val roseColor: TextView = itemView.findViewById(R.id.task_color_rose)

            fun bind(position: Int) {
                val recyclerViewModel = (completedlist[position])
                taskText.text = recyclerViewModel.title

                Log.e("color", "${recyclerViewModel.color}")

                if (recyclerViewModel.datetime == "") {
                    dateTime.visibility = View.GONE
                } else {
                    dateTime.visibility = View.VISIBLE
                    dateTime.text = recyclerViewModel.datetime
                }
                if (recyclerViewModel.color == "#ff453a") {
                    Log.e("color", "${recyclerViewModel.color}")
                    redColor.visibility = View.VISIBLE
                } else if (recyclerViewModel.color == "#ff9f0c") {
                    orangeColor.visibility = View.VISIBLE
                } else if (recyclerViewModel.color == "#ffd50c") {
                    yellowColor.visibility = View.VISIBLE
                } else if (recyclerViewModel.color == "#32d74b") {
                    greenColor.visibility = View.VISIBLE
                } else if (recyclerViewModel.color == "#64d2ff") {
                    mkrtaliBlueColor.visibility = View.VISIBLE
                } else if (recyclerViewModel.color == "#0984ff") {
                    blueColor.visibility = View.VISIBLE
                } else if (recyclerViewModel.color == "#5e5ce6") {
                    muqiBlueColor.visibility = View.VISIBLE
                } else if (recyclerViewModel.color == "#bf5af2") {
                    purpleColor.visibility = View.VISIBLE
                } else if (recyclerViewModel.color == "#ff375f") {
                    roseColor.visibility = View.VISIBLE
                } else {
                    redColor.visibility = View.INVISIBLE
                    orangeColor.visibility = View.INVISIBLE
                    yellowColor.visibility = View.INVISIBLE
                    mkrtaliBlueColor.visibility = View.INVISIBLE
                    blueColor.visibility = View.INVISIBLE
                    muqiBlueColor.visibility = View.INVISIBLE
                    greenColor.visibility = View.INVISIBLE
                    purpleColor.visibility = View.INVISIBLE
                    roseColor.visibility = View.INVISIBLE
                }

                Log.e("color", "${recyclerViewModel.color}")
            }


            fun completeTask(task: Taskie) {
                itemView.checkBox.setOnClickListener {
                    task.viewType = 2

                    

                    Log.e("color", "${task.color}")
//                notifyItemChanged(task.taskId)
                    notifyDataSetChanged()
                }
            }


    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    inner class ViewHolderCompletedTasks(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var message : TextView = itemView.findViewById(R.id.selected_task)

        fun bind(position: Int) {
            val recyclerViewModel = completedlist[position]
            message.text = recyclerViewModel.title
        }

        fun uncompleteTask(task: Taskie) {
            itemView.checkBox2.setOnClickListener {
                task.viewType = 1
//                notifyItemChanged(task.taskId)
                notifyDataSetChanged()
            }
        }

    }

    inner class ViewHolderCompletedHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
    inner class ViewHolderUncompletedHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == VIEW_TYPE_ONE) {

            return ViewHolderUncompletedTasks(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.todo_list_view_holder,
                    parent,
                    false
                )
            )
        }
        else if (viewType == VIEW_TYPE_HEADER_UNCOMPLITED) {
            return  ViewHolderUncompletedHeader(
                LayoutInflater.from(parent.context).inflate(R.layout.header_item, parent, false)
            )
        }
        else if (viewType == VIEW_TYPE_TWO){
            return  ViewHolderCompletedTasks(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.chechked_task_viewholder,
                    parent,
                    false
                )
            )
        }
        return ViewHolderCompletedHeader(
            LayoutInflater.from(parent.context).inflate(R.layout.header_item, parent, false)
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {



            if (position == (completedlist.lastIndex + 1)) {
                (holder as ViewHolderUncompletedHeader)
            }
        else if (completedlist[position].viewType == VIEW_TYPE_ONE){
            (holder as ViewHolderUncompletedTasks).bind(position)

            Log.e("color", "${completedlist[position].color}")
            holder.itemView.linearLayout.setBackgroundResource(R.drawable.viewholder_background)
            holder.itemView.checkBox.setButtonDrawable(R.drawable.unselected_task_checkbox)


            val currentItem = (completedlist[position])
            holder.completeTask(completedlist[position])
            holder.select(currentItem, position)
            Log.e("position ", "${completedlist[position]}")


            
                holder.itemView.checkBox.isChecked = false



//            if (currentItem.color == "#ff453a") {
//                holder.itemView.task_color_red.visibility = View.VISIBLE
//            } else if (currentItem.color == "#ff9f0c") {
//                holder.itemView.task_color_orange.visibility = View.VISIBLE
//            } else if (currentItem.color == "#ffd50c") {
//                holder.itemView.task_color_yellow.visibility = View.VISIBLE
//            } else if (currentItem.color == "#32d74b") {
//                holder.itemView.task_color_green.visibility = View.VISIBLE
//            } else if (currentItem.color == "#64d2ff") {
//                holder.itemView.task_color_pachtiblue.visibility = View.VISIBLE
//            } else if (currentItem.color == "#0984ff") {fdfdf
//                holder.itemView.task_color_blue.visibility = View.VISIBLE
//            } else if (currentItem.color == "#5e5ce6") {
//                holder.itemView.task_color_muqiblue.visibility = View.VISIBLE
//            } else if (currentItem.color == "#bf5af2") {
//                holder.itemView.task_color_purple.visibility = View.VISIBLE
//            } else if (currentItem.color == "#ff375f") {
//                holder.itemView.task_color_rose.visibility = View.VISIBLE
//            } else {
//                holder.itemView.task_color_red.visibility = View.INVISIBLE
//                holder.itemView.task_color_orange.visibility = View.INVISIBLE
//                holder.itemView.task_color_yellow.visibility = View.INVISIBLE
//                holder.itemView.task_color_pachtiblue.visibility = View.INVISIBLE
//                holder.itemView.task_color_blue.visibility = View.INVISIBLE
//                holder.itemView.task_color_muqiblue.visibility = View.INVISIBLE
//                holder.itemView.task_color_green.visibility = View.INVISIBLE
//                holder.itemView.task_color_purple.visibility = View.INVISIBLE
//                holder.itemView.task_color_rose.visibility = View.INVISIBLE
//            }

        }
        else if (completedlist[position].viewType == VIEW_TYPE_TWO){
            (holder as ViewHolderCompletedTasks).bind(position)


            holder.uncompleteTask(completedlist[position])


            holder.itemView.checkBox2.isChecked = true


        }



    }

    override fun getItemCount(): Int = (completedlist.size +1)

    override fun getItemViewType(position: Int): Int {

        if (position == (completedlist.lastIndex +1)) {
            return VIEW_TYPE_HEADER_UNCOMPLITED
        }

        return completedlist[position].viewType


    }


    fun setData(task: List<Taskie>) {
        this.completedlist.clear()
        this.completedlist.addAll(task)
        notifyDataSetChanged()
    }
    fun swap(list: ArrayList<Taskie>, i: Int, a: Int) {
        Collections.swap(list, i, a)
        notifyItemMoved(i, a)
    }
}