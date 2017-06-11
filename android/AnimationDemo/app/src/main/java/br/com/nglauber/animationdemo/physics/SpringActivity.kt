package br.com.nglauber.animationdemo.physics

import android.os.Bundle
import android.support.animation.SpringAnimation
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import br.com.nglauber.animationdemo.R
import kotlinx.android.synthetic.main.activity_spring.*

// https://gist.github.com/nickbutcher/7fdce476aaa589680cdd626d78e3149d
class SpringActivity : AppCompatActivity() {

    private var downX: Float = 0f
    private var downY: Float = 0f
    private var velocityTracker: VelocityTracker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spring)

        velocityTracker = VelocityTracker.obtain()

        img_bazinga.setOnTouchListener { v, event -> onTouch(v, event) }
    }

    fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                velocityTracker?.addMovement(event)
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                img_bazinga.translationX = event.x - downX
                img_bazinga.translationY = event.y - downY
                velocityTracker?.addMovement(event)
                return true
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                velocityTracker!!.computeCurrentVelocity(1000)
                if (img_bazinga.translationX != 0f) {
                    val animX = SpringAnimation(img_bazinga, SpringAnimation.TRANSLATION_X, 0f)
                    animX.spring.stiffness = getStiffness()
                    animX.spring.dampingRatio = getDamping()
                    animX.setStartVelocity(velocityTracker!!.xVelocity)
                    animX.start()
                }
                if (img_bazinga.translationY != 0f) {
                    val animY = SpringAnimation(img_bazinga, SpringAnimation.TRANSLATION_Y, 0f)
                    animY.spring.stiffness = getStiffness()
                    animY.spring.dampingRatio = getDamping()
                    animY.setStartVelocity(velocityTracker?.yVelocity ?: 0f)
                    animY.start()
                }
                velocityTracker?.clear()
                return true
            }
        }
        return false
    }

    private fun getStiffness(): Float {
        return Math.max(stiffness.progress.toFloat(), 1f)
    }

    private fun getDamping(): Float {
        return damping.progress / 100f
    }
}
