package br.com.nglauber.animationdemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import br.com.nglauber.animationdemo.iconanimation.IconAnimationActivity
import br.com.nglauber.animationdemo.lottieanimation.LottieActivity
import br.com.nglauber.animationdemo.material.Material1Activity
import br.com.nglauber.animationdemo.material.RevealActivityAnimationActivity
import br.com.nglauber.animationdemo.material.RevealAnimationActivity
import br.com.nglauber.animationdemo.physics.SpringActivity
import br.com.nglauber.animationdemo.propertyanimation.EvaluatorActivity
import br.com.nglauber.animationdemo.propertyanimation.ViewPropertyAnimatorActivity
import br.com.nglauber.animationdemo.transition.enghaw.ListAlbumsActivity
import br.com.nglauber.animationdemo.transition.scene.TransitionFormActivity
import br.com.nglauber.animationdemo.transition.scene.TransitionSceneActivity
import br.com.nglauber.animationdemo.viewanimation.InterpolatorActivity
import br.com.nglauber.animationdemo.viewanimation.ViewAnimationsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val activities = arrayOf<Class<*>>(
            ViewAnimationsActivity::class.java,
            InterpolatorActivity::class.java,
            ViewPropertyAnimatorActivity::class.java,
            EvaluatorActivity::class.java,
            TransitionFormActivity::class.java,
            TransitionSceneActivity::class.java,
            ListAlbumsActivity::class.java,
            IconAnimationActivity::class.java,
            Material1Activity::class.java,
            RevealAnimationActivity::class.java,
            RevealActivityAnimationActivity::class.java,
            LottieActivity::class.java,
            SpringActivity::class.java
    )

    private val options = arrayOf(
            "Animation",
            "Interpolators",
            "Property Animations",
            "ARGB Evaluator",
            "Transition Form",
            "Transition Scene",
            "Activity Transitions",
            "Icon Animation",
            "Material 1",
            "Reveal Animation",
            "Reveal Activity",
            "Lottie",
            "Spring"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_listview.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, options)
        main_listview.setOnItemClickListener { parent, view, position, id ->
            startActivity(Intent(this, activities[position]))
        }
    }
}
