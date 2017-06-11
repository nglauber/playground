package br.com.nglauber.animationdemo.iconanimation

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import br.com.nglauber.animationdemo.R
import kotlinx.android.synthetic.main.activity_icon_animation.*

class IconAnimationActivity : AppCompatActivity() {

    var isPlaying: Boolean = false
    var isPlayingFake: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_icon_animation)

        imageAnimatedVector.setOnClickListener { onClick() }
        imageVector.setOnClickListener { onClickNoAnim() }
        imageClock.setOnClickListener { onClockClick() }
    }

    fun onClick() {
        togglePlayPause()
    }

    fun onClickNoAnim() {
        isPlayingFake = !isPlayingFake
        if (isPlayingFake) {
            imageVector.setImageResource(R.drawable.ic_vector_pause)
        } else {
            imageVector.setImageResource(R.drawable.ic_vector_play)
        }
    }

    fun onClockClick() {
        val icon = imageClock.drawable
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (icon is AnimatedVectorDrawable) {
                icon.start()
            }
        }
    }

    fun getIconDrawable(): Drawable? {
        val drawableId: Int
        if (isPlaying) {
            drawableId = R.drawable.ic_play
        } else {
            drawableId = R.drawable.ic_pause
        }
        return ResourcesCompat.getDrawable(resources, drawableId, theme)
    }

    private fun togglePlayPause() {
        isPlaying = !isPlaying
        imageAnimatedVector.setImageDrawable(getIconDrawable())
        animateButton(imageAnimatedVector.drawable)
    }

    private fun animateButton(icon: Drawable?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && icon is AnimatedVectorDrawable) {
            icon.start()
        }
    }
    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
