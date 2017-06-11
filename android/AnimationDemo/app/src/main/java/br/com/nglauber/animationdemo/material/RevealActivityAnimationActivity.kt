package br.com.nglauber.animationdemo.material

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import br.com.nglauber.animationdemo.R
import kotlinx.android.synthetic.main.activity_reveal_activity_animation.*


class RevealActivityAnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reveal_activity_animation)

        fabAdd.setOnClickListener { showScreen() }
    }

    private fun showScreen() {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair(fabAdd, "circular")).toBundle()
        startActivity(Intent(this, RevealDetailActivity::class.java), options)
    }
}
