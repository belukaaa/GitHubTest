package com.raywenderlich.ticky.fragments

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.AssetManager
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.raywenderlich.ticky.R
import kotlinx.android.synthetic.main.onboarding.*
import kotlinx.android.synthetic.main.onboarding.view.*

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
           // myFirstTryToAnimate()
          // mySecondTryToAnimate()

          listener?.buttonClicked()

        }

    }

    private fun mySecondTryToAnimate() {

        val rootHeight = onboarding_button.maxHeight

        val centerx = onboarding_button.width
        val centery = onboarding_button.width

        val circularReveal = ViewAnimationUtils.createCircularReveal(onboarding_button , centerx, centery, 0f , rootHeight.toFloat() )


        circularReveal.duration = 8000
      //  onboarding_button.visibility = INVISIBLE
        circularReveal.start()
    }

    private fun myFirstTryToAnimate() {

        val onboardingButtonAnimator = ObjectAnimator.ofFloat(onboarding_button , "alpha" , 0f , 1f)
        onboardingButtonAnimator.duration = 5000
        onboardingButtonAnimator.interpolator = BounceInterpolator()

        onboardingButtonAnimator.start()

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