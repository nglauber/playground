package br.com.nglauber.animationdemo.transition

import android.os.Build
import android.support.annotation.RequiresApi
import android.transition.Transition

@RequiresApi(Build.VERSION_CODES.KITKAT)
open class TransitionListenerAdapter : Transition.TransitionListener {
    override fun onTransitionEnd(transition: Transition?) {
    }

    override fun onTransitionResume(transition: Transition?) {
    }

    override fun onTransitionPause(transition: Transition?) {
    }

    override fun onTransitionCancel(transition: Transition?) {
    }

    override fun onTransitionStart(transition: Transition?) {
    }
}