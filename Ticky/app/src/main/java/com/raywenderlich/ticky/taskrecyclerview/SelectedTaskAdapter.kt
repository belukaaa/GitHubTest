package com.raywenderlich.ticky.taskrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.ticky.R
import com.raywenderlich.ticky.Taskie
import kotlinx.android.synthetic.main.chechked_task_viewholder.view.*

class SelectedTaskAdapter : RecyclerView.Adapter<SelectedTaskAdapter.SelectTaskViewHolder>() {

    private var selectedList = ArrayList<Taskie>()
    private var unselectedList = ArrayList<Taskie>()

    inner class SelectTaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        var selectedCheckbox = itemView.selected_task

        fun unSelectView(task: Taskie) {

            val checkbox = itemView.checkBox2
            checkbox.setOnCheckedChangeListener { _, _ ->
                if (task.selected) {

                    checkbox.isChecked = true
                    task.selected = false
                    selectedList.remove(task)
                    unselectedList.add(task)
                    listener?.unSelectSelected(unselectedList)

                    notifyDataSetChanged()
                }
                else{
                    task.selected = true
                    unselectedList.remove(task)
                    listener?.unSelectSelected(unselectedList)

                    notifyDataSetChanged()

                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectTaskViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.chechked_task_viewholder , parent , false)



        return SelectTaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectTaskViewHolder, position: Int) {

        val currentItem = (selectedList[position])

        holder.unSelectView(currentItem)

        holder.selectedCheckbox.text = currentItem.title



    }

    override fun getItemCount(): Int = selectedList.size

    fun setSelectedData(task: List<Taskie>) {
        this.selectedList.clear()
        this.selectedList.addAll(task)
        notifyDataSetChanged()

    }
    fun unSelectData(task: List<Taskie>){
        this.unselectedList.clear()
        this.unselectedList.addAll(task)
        notifyDataSetChanged()
    }

    var listener : unSelectListener ? = null


    interface unSelectListener {
        fun unSelectSelected(list: List<Taskie>)
    }

    fun setOnCheckListener(listener: unSelectListener) {
        this.listener = listener
    }
}