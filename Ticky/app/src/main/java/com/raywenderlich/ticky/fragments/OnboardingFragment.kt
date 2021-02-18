package com.raywenderlich.ticky.fragments

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.raywenderlich.ticky.R
import kotlinx.android.synthetic.main.onboarding.*

class OnboardingFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = LayoutInflater.from(context).inflate(R.layout.onboarding , container , false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onboarding_button.setOnClickListener {
            listener?.buttonClicked()
        }




    }

    companion object {
        fun getFirstFragInstance(): OnboardingFragment {
            return OnboardingFragment()
        }
    }

    private var listener : ButtonClicked? = null
    interface ButtonClicked {
        fun buttonClicked ()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ButtonClicked
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


}