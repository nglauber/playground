package br.com.nglauber.animationdemo.viewanimation

import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import br.com.nglauber.animationdemo.R
import kotlinx.android.synthetic.main.activity_interpolator.*
import java.util.*

class InterpolatorActivity : AppCompatActivity() {

    val interpolators = listOf<Interpolator>(
            LinearInterpolator(),
            AccelerateDecelerateInterpolator(),
            BounceInterpolator(),
            AccelerateInterpolator(1.0f),
            AnticipateInterpolator(2.0f),
            AnticipateOvershootInterpolator(2.0f, 1.5f),
            DecelerateInterpolator(1.0f),
            OvershootInterpolator(2.0f),
            FastOutLinearInInterpolator(),
            FastOutSlowInInterpolator(),
            LinearOutSlowInInterpolator()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interpolator)

        interpolators.forEach {
            val view = View(this)
            view.layoutParams = ViewGroup.LayoutParams(96, 96)
            view.setBackgroundColor(generateColor())
            viewContainer.addView(view)
        }

        button.setOnClickListener { animateAll() }
    }

    private fun  animateAll() {
        for (i in 0..viewContainer.childCount-1) {
            val translateAnim = TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 5.0f)
            translateAnim.interpolator = interpolators[i]
            translateAnim.duration = 1000L
            translateAnim.repeatMode = Animation.REVERSE
            translateAnim.repeatCount = 1

            viewContainer.getChildAt(i).startAnimation(translateAnim)
        }
    }

    fun generateColor() : Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }
}
