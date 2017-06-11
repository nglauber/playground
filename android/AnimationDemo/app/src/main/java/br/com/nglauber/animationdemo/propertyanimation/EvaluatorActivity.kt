package br.com.nglauber.animationdemo.propertyanimation

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.nglauber.animationdemo.R
import kotlinx.android.synthetic.main.activity_evaluator.*

class EvaluatorActivity : AppCompatActivity() {

    lateinit var anim : ObjectAnimator
    var reverse = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluator)

        anim = ObjectAnimator.ofObject(txt_color_anim, "textColor", ArgbEvaluator(), txt_color_anim.currentTextColor, Color.RED)
        txt_color_anim.setOnClickListener { textClick() }
    }

    fun textClick(){
        if (reverse) anim.reverse() else anim.start()
        reverse = !reverse
    }
}
