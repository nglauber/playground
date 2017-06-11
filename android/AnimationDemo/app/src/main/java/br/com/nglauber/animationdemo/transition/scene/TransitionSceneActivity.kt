package br.com.nglauber.animationdemo.transition.scene

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.AutoTransition
import android.transition.Scene
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import br.com.nglauber.animationdemo.R

class TransitionSceneActivity : AppCompatActivity() {

    lateinit var sceneRoot : ViewGroup
    var edit: EditText? = null

    var more : Boolean = false

    lateinit var sceneMore : Scene
    lateinit var sceneLess : Scene
    lateinit var layoutMore : ViewGroup
    lateinit var layoutLess : ViewGroup

    var texto : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition_scene)

        sceneRoot = findViewById(R.id.scene_root) as ViewGroup
        initScenes()
        changeScene()
    }

    fun initScenes() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            sceneMore = Scene.getSceneForLayout(sceneRoot, R.layout.activity_transition_scene_more, this)
            sceneLess = Scene.getSceneForLayout(sceneRoot, R.layout.activity_transition_scene_less, this)
        } else {
            layoutMore = LayoutInflater.from(this).inflate(R.layout.activity_transition_scene_more, sceneRoot, false) as ViewGroup
            layoutLess = LayoutInflater.from(this).inflate(R.layout.activity_transition_scene_less, sceneRoot, false) as ViewGroup
        }
    }

    fun onMoreClick() {
        more = !more
        changeScene()
    }

    fun changeScene() {
        if (edit != null){
            texto = edit?.text.toString()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.go(if (more) sceneMore else sceneLess, AutoTransition())
        } else {
            sceneRoot.removeAllViews()
            sceneRoot.addView(if(more) layoutMore else layoutLess)
        }
        edit = findViewById(R.id.editText) as? EditText
        edit?.setText(texto)
        findViewById(R.id.btnMoreLess).setOnClickListener { onMoreClick() }
    }
}
