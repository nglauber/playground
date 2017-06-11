package br.com.nglauber.animationdemo.material

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.transition.TransitionManager
import android.view.View
import br.com.nglauber.animationdemo.R
import kotlinx.android.synthetic.main.activity_material1.*
import kotlinx.android.synthetic.main.layout_card_material1.*

class Material1Activity : AppCompatActivity() {

    var expanded = true
    val constraintSet = ConstraintSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material1)

        cardViewCity.setOnClickListener { animateCard() }
        Handler().postDelayed( { animateCard() }, 50)
    }

    fun animateCard() {
        val lp = cardViewCity.layoutParams
        val lp2 = imageViewPhoto.layoutParams
        constraintSet.clone(constraintlayout)

        expanded = !expanded
        if (expanded) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && checkBoxAnimate.isChecked) {
                TransitionManager.beginDelayedTransition(constraintlayout)
            }
            constraintSet.connect(R.id.cardViewCity, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)
            constraintSet.setVerticalBias(R.id.cardViewCity, 0.5f)
            constraintSet.applyTo(constraintlayout)

            lp.width = cardViewCity.width * 2
            cardViewCity.layoutParams = lp

            lp2.height = imageViewPhoto.height * 2
            imageViewPhoto.layoutParams = lp2
            textViewState.visibility = View.VISIBLE
            textViewDescription.visibility = View.VISIBLE

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && checkBoxAnimate.isChecked) {
                TransitionManager.beginDelayedTransition(constraintlayout)
            }
            constraintSet.clear(R.id.cardViewCity, ConstraintSet.RIGHT)
            constraintSet.setVerticalBias(R.id.cardViewCity, 1.0f)
            constraintSet.applyTo(constraintlayout)

            lp.width = cardViewCity.width / 2
            cardViewCity.layoutParams = lp

            lp2.height = imageViewPhoto.height / 2
            imageViewPhoto.layoutParams = lp2
            textViewState.visibility = View.GONE
            textViewDescription.visibility = View.GONE
        }
    }
}
