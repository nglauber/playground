package br.com.nglauber.snacksample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.TextView
import androidx.annotation.LayoutRes

class FakeSnackBar private constructor() {
    lateinit var swipeView: SwipeView
        private set
    private var viewReady = false
    private var showRequested = false

    fun show() {
        showRequested = true
        if (viewReady) {
            swipeView.show()
        }
    }

    fun dismiss() {
        showRequested = false
        swipeView.dismiss()
    }

    companion object {
        fun make(
            parent: ViewGroup,
            text: String,
            actionText: String? = null,
            action: (() -> Unit)? = null
        ): FakeSnackBar {
            val snack = FakeSnackBar()
            val context = parent.context
            val swipeView = buildSwipeView(context, snack)
            val content = initDefaultContent(context, parent, text, actionText) {
                action?.invoke()
                snack.dismiss()
            }
            setupViews(swipeView, content, snack, parent)
            return snack
        }

        fun make(
            parent: ViewGroup,
            @LayoutRes layout: Int
        ): FakeSnackBar {
            val snack = FakeSnackBar()
            val context = parent.context
            val swipeView = buildSwipeView(context, snack)
            val content = LayoutInflater.from(context)
                .inflate(layout, parent, false)
            setupViews(swipeView, content, snack, parent)
            return snack
        }

        private fun setupViews(
            swipeView: SwipeView,
            content: View?,
            snack: FakeSnackBar,
            parent: ViewGroup
        ) {
            swipeView.addView(content)
            snack.swipeView = swipeView
            parent.addView(swipeView)
        }

        private fun buildSwipeView(context: Context, snack: FakeSnackBar): SwipeView {
            val swipeView = SwipeView(context).apply {
                visibility = View.INVISIBLE
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
            swipeView.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    swipeView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    snack.viewReady = true
                    if (snack.showRequested) {
                        snack.show()
                    }
                }
            })
            return swipeView
        }

        private fun initDefaultContent(
            context: Context,
            parent: ViewGroup,
            message: String,
            textAction: String?,
            action: () -> Unit
        ) = LayoutInflater
            .from(context)
            .inflate(R.layout.mysnack, parent, false).apply {
                findViewById<TextView>(R.id.txtMessage).text = message
                if (textAction != null) {
                    findViewById<Button>(R.id.btnAction).apply {
                        text = textAction
                        setOnClickListener {
                            action()
                        }
                    }
                }
            }

    }
}