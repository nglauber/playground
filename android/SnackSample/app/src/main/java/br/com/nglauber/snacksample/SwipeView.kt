package br.com.nglauber.snacksample

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.widget.FrameLayout
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.updateLayoutParams
import com.google.android.material.animation.AnimationUtils
import kotlin.math.abs

class SwipeView(context: Context) : FrameLayout(context) {

    private var originX = 0

    private val gestureDetector = GestureDetectorCompat(context, object : SwipeDetector() {
        override fun onSwipeLeft(event: MotionEvent) {
            animateView(this@SwipeView, SwipeAnimationDirection.LEFT)
        }

        override fun onSwipeRight(event: MotionEvent) {
            animateView(this@SwipeView, SwipeAnimationDirection.RIGHT)
        }
    })

    init {
        setOnTouchListener(object : OnTouchListener {
            override fun onTouch(view: View, event: MotionEvent): Boolean {
                if (gestureDetector.onTouchEvent(event)) {
                    return true
                }
                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN ->
                        originX = event.x.toInt()
                    MotionEvent.ACTION_MOVE -> {
                        view.updateLayoutParams<MarginLayoutParams> {
                            this.leftMargin += (event.x - originX).toInt()
                            this.rightMargin -= (event.x - originX).toInt()
                        }
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL ->
                        handleTouchUp(this@SwipeView, event)
                }
                return true
            }
        })
    }

    fun show() {
        animateView(this, SwipeAnimationDirection.UP)
    }

    fun dismiss() {
        animateView(this, SwipeAnimationDirection.DOWN)
    }

    private fun handleTouchUp(vw: View, event: MotionEvent) {
        when {
            event.rawX < originX && abs(event.rawX - originX) > DISMISS_SWIPE_DISTANCE ->
                animateView(vw, SwipeAnimationDirection.LEFT)

            event.rawX > originX && abs(event.rawX - originX) > DISMISS_SWIPE_DISTANCE ->
                animateView(vw, SwipeAnimationDirection.RIGHT)
            else ->
                animateView(vw, SwipeAnimationDirection.UNDO)

        }
    }

    private fun animateView(
        vw: View,
        direction: SwipeAnimationDirection,
        duration: Long = SWIPE_ANIMATION_DURATION
    ) {
        val parent = vw.parent as ViewGroup? ?: return

        val animationListener = object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(a: Animator?) {
                parent.removeView(vw)
            }
        }
        val animator = vw.animate()
            .setDuration(duration)
            .setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR)

        when (direction) {
            SwipeAnimationDirection.LEFT ->
                moveHorizontal(-vw.width.toFloat(), animator, animationListener)

            SwipeAnimationDirection.RIGHT ->
                moveHorizontal(parent.width.toFloat(), animator, animationListener)

            SwipeAnimationDirection.UNDO ->
                animator.x(0f)

            SwipeAnimationDirection.DOWN ->
                moveDown(parent, animator, animationListener)

            SwipeAnimationDirection.UP ->
                moveUp(vw, parent, animator)
        }
    }

    private fun moveHorizontal(
        translationX: Float,
        animator: ViewPropertyAnimator,
        animationListener: Animator.AnimatorListener
    ) {
        animator
            .translationX(translationX)
            .setListener(animationListener)
    }

    private fun moveDown(
        parent: ViewGroup,
        animator: ViewPropertyAnimator,
        animationListener: Animator.AnimatorListener
    ) {
        animator
            .translationY(parent.height.toFloat())
            .setListener(animationListener)
        animator.start()
    }

    private fun moveUp(vw: View, parent: ViewGroup, animator: ViewPropertyAnimator) {
        var bottomMargin = 0
        if (parent.layoutParams is MarginLayoutParams) {
            bottomMargin = (parent.layoutParams as MarginLayoutParams).bottomMargin
        }
        vw.y = parent.height.toFloat()
        vw.visibility = View.VISIBLE
        animator.y(parent.height - vw.height.toFloat() + bottomMargin)
        animator.start()
    }

    enum class SwipeAnimationDirection {
        LEFT, RIGHT, UNDO, DOWN, UP
    }

    companion object {
        private const val DISMISS_SWIPE_DISTANCE = 300
        private const val SWIPE_ANIMATION_DURATION = 250L
    }
}