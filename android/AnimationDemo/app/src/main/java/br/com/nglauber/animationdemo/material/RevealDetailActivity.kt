package br.com.nglauber.animationdemo.material

import android.os.Build
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.transition.Transition
import android.view.View
import android.view.ViewAnimationUtils
import br.com.nglauber.animationdemo.R
import br.com.nglauber.animationdemo.transition.TransitionListenerAdapter
import kotlinx.android.synthetic.main.activity_reveal_detail.*

class RevealDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reveal_detail)

        ViewCompat.setTransitionName(viewPlaceHolder, "circular")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && savedInstanceState == null) {

            viewTop.visibility = View.INVISIBLE

            window.sharedElementEnterTransition.addListener(object: TransitionListenerAdapter() {
                override fun onTransitionEnd(transition: Transition?) {
                    super.onTransitionEnd(transition)
                    window.sharedElementEnterTransition.removeListener(this)
                    viewPlaceHolder.post { viewPlaceHolder.visibility = View.INVISIBLE }
                    circularRevealActivity()
                }
            })
        }
    }

    private fun circularRevealActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val cx = viewTop.left + (viewTop.width / 2)
            val cy = viewTop.top + (viewTop.height / 2)

            val finalRadius = Math.max(viewTop.width, viewTop.height)

            val circularReveal = ViewAnimationUtils.createCircularReveal(viewTop, cx, cy, 0f, finalRadius.toFloat())
//            circularReveal.addListener(object: AnimatorListenerAdapter() {
//                override fun onAnimationStart(animation: Animator?) {
//                    super.onAnimationStart(animation)
//                    viewPlaceHolder.visibility = View.INVISIBLE
//                }
//            })
            viewTop.visibility = View.VISIBLE
            circularReveal.start()
        }
    }

    override fun onBackPressed() {
        viewPlaceHolder.visibility = View.VISIBLE
        super.onBackPressed()
    }
}
