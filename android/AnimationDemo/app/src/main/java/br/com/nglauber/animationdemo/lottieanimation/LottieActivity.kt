package br.com.nglauber.animationdemo.lottieanimation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import br.com.nglauber.animationdemo.R
import kotlinx.android.synthetic.main.activity_lottie.*

class LottieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie)

        lottieSpring.setAnimation("lottie/spring_animation.json")
        lottieSpring.loop(true)
        lottieSpring.playAnimation()
    }

    companion object {

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
