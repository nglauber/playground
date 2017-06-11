package br.com.nglauber.animationdemo.viewanimation

import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.support.v7.app.AppCompatActivity
import android.view.animation.*
import br.com.nglauber.animationdemo.R
import kotlinx.android.synthetic.main.activity_view_animations.*

class ViewAnimationsActivity : AppCompatActivity() {

    private var animations: Array<Animation> = emptyArray()
    private var animationsXml: Array<Animation> = emptyArray()
    private var interpolators: Array<Interpolator> = emptyArray()

    public override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_animations)

        initInterpolators()
        initAnimations()

        img_bazinga.setOnClickListener { executeAnimation() }
        button_next.setOnClickListener { nextScreen() }
    }

    fun nextScreen() {
        val it = android.content.Intent(this, FrameByFrameAnimationActivity::class.java)
        startActivity(it)
        overridePendingTransition(R.anim.scale_up, R.anim.slide_out_left)
    }

    private fun initAnimations() {
        // From Xml
        animationsXml = arrayOf(
            AnimationUtils.loadAnimation(this, R.anim.anim_fade),
            AnimationUtils.loadAnimation(this, R.anim.anim_rotation),
            AnimationUtils.loadAnimation(this, R.anim.anim_scale),
            AnimationUtils.loadAnimation(this, R.anim.anim_translation),
            AnimationUtils.loadAnimation(this, R.anim.anim_set)
        )

        // From Code
        val DURACAO_ANIMACAO = 1000

        val alphaAnim = AlphaAnimation(1f, 0f)
        alphaAnim.duration = 1000
        val rotateAnim = RotateAnimation(
                0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f)
        val scaleAnim = ScaleAnimation(
                1f, 3f, 1f, 3f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f)
        val translateAnim = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 2.0f)

        val set = AnimationSet(true)
        set.addAnimation(alphaAnim)
        set.addAnimation(rotateAnim)
        set.addAnimation(scaleAnim)
        set.addAnimation(translateAnim)

        animations = arrayOf( alphaAnim, rotateAnim, scaleAnim, translateAnim, set)

        for (i in 0..animations.size - 2) {
            animations[i].duration = DURACAO_ANIMACAO.toLong()
            animations[i].repeatMode = Animation.REVERSE
            animations[i].repeatCount = 1
        }
    }

    private fun initInterpolators() {
        interpolators = arrayOf(
                AccelerateDecelerateInterpolator(),
                AccelerateInterpolator(1.0f), // <- factor (optional)
                AnticipateInterpolator(2.0f), // <- tension (optional)
                AnticipateOvershootInterpolator(2.0f, 1.5f), // <- tension / extra tension (optional)
                BounceInterpolator(),
                CycleInterpolator(2f), // <- cycles
                DecelerateInterpolator(1.0f), // <- factor (optional)
                LinearInterpolator(),
                OvershootInterpolator(2.0f), // <- tension (optional)
                FastOutLinearInInterpolator(),
                FastOutSlowInInterpolator(),
                LinearOutSlowInInterpolator()
        )
    }

    private fun executeAnimation() {
        val animationIndex = spn_animations.selectedItemPosition
        val interpolator = interpolators[spn_interpolators.selectedItemPosition]
        val animation = if (chkUseXml.isChecked)
            animationsXml[animationIndex]
        else
            animations[animationIndex]

        animation.interpolator = interpolator
        img_bazinga.startAnimation(animation)
    }
}
