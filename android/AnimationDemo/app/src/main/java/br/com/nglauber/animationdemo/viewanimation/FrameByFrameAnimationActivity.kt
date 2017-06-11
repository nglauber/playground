package br.com.nglauber.animationdemo.viewanimation

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.nglauber.animationdemo.R
import kotlinx.android.synthetic.main.activity_frame_by_frame_animation.*

class FrameByFrameAnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame_by_frame_animation)

        imageViewSprite.setOnClickListener {
            val spriteAnimation = imageViewSprite.drawable as AnimationDrawable

            if (spriteAnimation.isRunning){
                spriteAnimation.stop()
            } else {
                spriteAnimation.start()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_rigth, R.anim.fade_out)
    }
}
