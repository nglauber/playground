package br.com.nglauber.exemplolivro.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import br.com.nglauber.exemplolivro.model.auth.AccessManager

open class BaseActivity : AppCompatActivity() {
    private var mAccessManager: AccessManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAccessManager = AccessManager.instance

        if (mAccessManager?.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
    }

    public override fun onStart() {
        super.onStart()
        mAccessManager?.addAccessChangedListener(mAuthListener)
    }

    public override fun onStop() {
        super.onStop()
        mAccessManager?.removeAccessChangedListener(mAuthListener)
    }

    private val mAuthListener = object : AccessManager.AccessChangedListener {
        override fun accessChanged(hasAccess: Boolean) {
            if (!hasAccess) {
                startActivity(Intent(this@BaseActivity, LoginActivity::class.java))
                finish()
            }
        }
    }
}
