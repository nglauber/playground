package br.com.nglauber.snacksample

import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.abs

// Converted to Kotlin from:
// https://stackoverflow.com/questions/6720138/swipe-left-right-up-and-down-depending-on-velocity-or-other-variables
abstract class SwipeDetector : GestureDetector.SimpleOnGestureListener() {

    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        val distance = e1.x - e2.x
        val enoughSpeed = abs(velocityX) > SWIPE_THRESHOLD_VELOCITY
        return if (distance > SWIPE_MIN_DISTANCE && enoughSpeed) {
            onSwipeLeft(e2)
            true
        } else if (distance < -SWIPE_MIN_DISTANCE && enoughSpeed) {
            onSwipeRight(e2)
            true
        } else {
            false
        }
    }

    abstract fun onSwipeLeft(event: MotionEvent)

    abstract fun onSwipeRight(event: MotionEvent)

    companion object {
        private const val SWIPE_MIN_DISTANCE = 120
        private const val SWIPE_THRESHOLD_VELOCITY = 200
    }
}