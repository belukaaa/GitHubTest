package com.raywenderlich.ticky.fragments

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import com.raywenderlich.ticky.R
import kotlinx.android.synthetic.main.dialog_fragment.*
import kotlinx.android.synthetic.main.dialog_fragment.view.*

class CustomDialogFragment : DialogFragment() {

    private var SELECTED_ID = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.dialog_fragment , container , false )

        dialog?.window?.setGravity(Gravity.BOTTOM)

        rootView.date_added.setOnClickListener {
            var selectedID = radio_group.checkedRadioButtonId
            val radio = rootView.findViewById<RadioButton>(selectedID)
            val result = radio.text.toString()
            SELECTED_ID = result
            listener?.sortBy(SELECTED_ID)
            dismiss()
        }

        rootView.due_date.setOnClickListener {
            val selectedID = radio_group.checkedRadioButtonId
            val radio = rootView.findViewById<RadioButton>(selectedID)
            var result = radio.text.toString()
            SELECTED_ID = result
            listener?.sortBy(SELECTED_ID)
            dismiss()
        }
        rootView.color_label.setOnClickListener{
            val selectedID = radio_group.checkedRadioButtonId
            val radio = rootView.findViewById<RadioButton>(selectedID)
            var result = radio.text.toString()
            SELECTED_ID = result
            listener?.sortBy(SELECTED_ID)
            dismiss()
        }

        return rootView
    }


    private var listener : DialogSorting? =null

    interface DialogSorting {
        fun sortBy(sort : String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as DialogSorting

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }



}