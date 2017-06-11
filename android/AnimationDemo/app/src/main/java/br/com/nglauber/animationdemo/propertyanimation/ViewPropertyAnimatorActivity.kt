package br.com.nglauber.animationdemo.propertyanimation

import android.animation.*
import android.os.Bundle
import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.*
import br.com.nglauber.animationdemo.R
import kotlinx.android.synthetic.main.activity_view_property_animator.*

class ViewPropertyAnimatorActivity : AppCompatActivity() {

    private var listAnimators: MutableList<Animator> = mutableListOf()
    private var listAnimatorsXml: MutableList<Animator> = mutableListOf()
    private var listInterpolators: Array<Interpolator> = emptyArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_property_animator)

        initInterpolators()
        initAnimations()
    }

    private fun initAnimations() {
        img_bazinga.setOnClickListener { executeAnimator() }

        // From Code
        var anim : ObjectAnimator = ObjectAnimator.ofFloat(img_bazinga, View.ALPHA, 0f)
        anim.duration = ANIMATION_DURATION.toLong()
        anim.repeatCount = 1
        anim.repeatMode = ValueAnimator.REVERSE
        listAnimators.add(anim)

        anim = ObjectAnimator.ofFloat(img_bazinga, View.ROTATION, 360f)
        anim.duration = ANIMATION_DURATION.toLong()
        anim.repeatCount = 1
        anim.repeatMode = ValueAnimator.REVERSE
        listAnimators.add(anim)

        val scaleX = ObjectAnimator.ofFloat(img_bazinga, View.SCALE_X, 2f)
        scaleX.repeatCount = 1
        scaleX.repeatMode = ValueAnimator.REVERSE
        scaleX.duration = ANIMATION_DURATION.toLong()

        val scaleY = ObjectAnimator.ofFloat(img_bazinga, View.SCALE_Y, 2f)
        scaleY.repeatCount = 1
        scaleY.repeatMode = ValueAnimator.REVERSE
        scaleY.duration = ANIMATION_DURATION.toLong()

        val set = AnimatorSet()
        set.playTogether(scaleX, scaleY)
        listAnimators.add(set)

        anim = ObjectAnimator.ofFloat(img_bazinga, View.TRANSLATION_X, 0f, 500f)
        anim.duration = ANIMATION_DURATION.toLong()
        anim.repeatMode = ValueAnimator.REVERSE
        anim.repeatCount = 1
        listAnimators.add(anim)

        // From Xml
        var animXml = AnimatorInflater.loadAnimator(this, R.animator.anim_fade)
        animXml.setTarget(img_bazinga)
        listAnimatorsXml.add(animXml)

        animXml = AnimatorInflater.loadAnimator(this, R.animator.anim_rotation)
        animXml.setTarget(img_bazinga)
        listAnimatorsXml.add(animXml)

        animXml = AnimatorInflater.loadAnimator(this, R.animator.anim_scale)
        animXml.setTarget(img_bazinga)
        listAnimatorsXml.add(animXml)

        animXml = AnimatorInflater.loadAnimator(this, R.animator.anim_translation)
        animXml.setTarget(img_bazinga)
        listAnimatorsXml.add(animXml)


        scaleX.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationCancel(animation: Animator) {
                super.onAnimationCancel(animation)
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
            }

            override fun onAnimationRepeat(animation: Animator) {
                super.onAnimationRepeat(animation)
            }

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
            }

            override fun onAnimationPause(animation: Animator) {
                super.onAnimationPause(animation)
            }

            override fun onAnimationResume(animation: Animator) {
                super.onAnimationResume(animation)
            }
        })
    }

    private fun initInterpolators() {
        listInterpolators = arrayOf(
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

    private fun executeAnimator() {
        val animationIndex = spn_animations.selectedItemPosition
        val interpolator = listInterpolators[spn_interpolators.selectedItemPosition]
        when (rg_animation_type.checkedRadioButtonId) {
            R.id.rb_obj_anim -> {
                val animator = listAnimators[animationIndex]
                animator.interpolator = interpolator
                animator.start()
            }

            R.id.rb_view_prop_anim -> viewPropertyAnimation(animationIndex, interpolator)

            R.id.rb_xml_anim -> {
                val animator = listAnimatorsXml[animationIndex]
                animator.interpolator = interpolator
                animator.start()
            }
        }

    }

    private fun viewPropertyAnimation(index: Int, interpolator: Interpolator) {
        when (index) {
            0 -> img_bazinga.animate()
                    .alpha(0f)
                    .setDuration(ANIMATION_DURATION.toLong())
                    .setInterpolator(interpolator)
                    .withEndAction {
                        img_bazinga.animate()
                                .alpha(1f)
                                .setDuration(ANIMATION_DURATION.toLong())
                                .start()
                    }

            1 -> img_bazinga.animate()
                    .rotation(360f)
                    .setDuration(ANIMATION_DURATION.toLong())
                    .withEndAction {
                        img_bazinga.animate()
                                .rotation(0f)
                                .setDuration(ANIMATION_DURATION.toLong())
                                .start()
                    }

            2 -> img_bazinga.animate()
                    .scaleX(2f)
                    .scaleY(2f)
                    .setDuration(ANIMATION_DURATION.toLong())
                    .withEndAction {
                        img_bazinga.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(ANIMATION_DURATION.toLong())
                                .start()
                    }
            3 -> img_bazinga.animate()
                    .translationX(500f)
                    .setDuration(ANIMATION_DURATION.toLong())
                    .withEndAction {
                        img_bazinga.animate()
                                .translationX(0f)
                                .setDuration(ANIMATION_DURATION.toLong())
                                .start()
                    }
        }
    }

    companion object {
        private val ANIMATION_DURATION = 1000
    }
}
