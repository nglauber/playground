package br.com.nglauber.animationdemo.material


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.transition.AutoTransition
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.view.ViewAnimationUtils
import br.com.nglauber.animationdemo.R
import br.com.nglauber.animationdemo.transition.TransitionListenerAdapter
import kotlinx.android.synthetic.main.activity_reveal_animation.*

class RevealAnimationActivity : AppCompatActivity() {

    var forward: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reveal_animation)

        fabAdd.setOnClickListener { displayCard() }
        cardViewCity.setOnClickListener { hideCard() }
    }

    private fun displayCard() {
        forward = true
        moveFab({
            fabAdd.visibility = View.INVISIBLE
            revealAnimation(fabAdd.width, cardViewCity.width)
        })
    }

    private fun hideCard() {
        forward = false
        revealAnimation(cardViewCity.width, fabAdd.width, {
            fabAdd.visibility = View.VISIBLE
            moveFab()
        })
    }


    private fun revealAnimation(initialRadius: Int, finalRadius: Int, endAction: (() -> Any)? = null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val cx = (cardViewCity.left + cardViewCity.right) / 2
            val cy = (cardViewCity.top + cardViewCity.bottom) / 2

            val anim = ViewAnimationUtils.createCircularReveal(cardViewCity, cx, cy, initialRadius.toFloat(), finalRadius.toFloat())

            if (endAction != null) {
                anim.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        if (!forward) cardViewCity.visibility =  View.INVISIBLE
                        endAction()
                    }
                })
            }

            if (forward) cardViewCity.visibility =  View.VISIBLE

            anim.start()

        } else {
            if (forward) {
                fabAdd.visibility = View.INVISIBLE
                cardViewCity.visibility = View.VISIBLE
            } else {
                fabAdd.visibility = View.VISIBLE
                cardViewCity.visibility = View.INVISIBLE
            }
        }
    }

    private fun moveFab(endAction : (()->Any)? = null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val transition = AutoTransition()
            transition.duration = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
            transition.pathMotion = GravityArcMotion()

            if (endAction != null) {
                transition.addListener(object : TransitionListenerAdapter() {
                    override fun onTransitionEnd(transition: Transition?) {
                        endAction()
                    }
                })
            }

            val constraintSet = ConstraintSet()
            constraintSet.clone(constraintlayout)

            TransitionManager.beginDelayedTransition(constraintlayout, transition)
            if (forward) {
                constraintSet.clear(R.id.fabAdd, ConstraintSet.BOTTOM)
                constraintSet.clear(R.id.fabAdd, ConstraintSet.RIGHT)
                constraintSet.connect(R.id.fabAdd, ConstraintSet.TOP, R.id.cardViewCity, ConstraintSet.TOP)
                constraintSet.connect(R.id.fabAdd, ConstraintSet.LEFT, R.id.cardViewCity, ConstraintSet.LEFT)
                constraintSet.connect(R.id.fabAdd, ConstraintSet.BOTTOM, R.id.cardViewCity, ConstraintSet.BOTTOM)
                constraintSet.connect(R.id.fabAdd, ConstraintSet.RIGHT, R.id.cardViewCity, ConstraintSet.RIGHT)
            } else {
                constraintSet.clear(R.id.fabAdd, ConstraintSet.BOTTOM)
                constraintSet.clear(R.id.fabAdd, ConstraintSet.RIGHT)
                constraintSet.clear(R.id.fabAdd, ConstraintSet.TOP)
                constraintSet.clear(R.id.fabAdd, ConstraintSet.LEFT)
                constraintSet.connect(R.id.fabAdd, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, resources.getDimension(R.dimen.default_margin).toInt())
                constraintSet.connect(R.id.fabAdd, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, resources.getDimension(R.dimen.default_margin).toInt())
            }
            constraintSet.applyTo(constraintlayout)

        } else {
            cardViewCity.visibility = View.VISIBLE
            fabAdd.visibility = View.INVISIBLE
        }
    }
}
