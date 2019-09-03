package br.com.nglauber.snacksample

import android.graphics.Point
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams

class ViewMove {
    var originPoint = Point()

    fun wrap(vw: View) {
        vw.setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    originPoint = Point(event.x.toInt(), event.y.toInt())
                }
                MotionEvent.ACTION_MOVE -> {
                    vw.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                        this.leftMargin += (event.x - originPoint.x).toInt()
                        this.topMargin += (event.y - originPoint.y).toInt()
                    }
                }
            }
            true
        }
    }
}