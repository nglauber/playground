package br.com.nglauber.exemplolivro.shared

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import br.com.nglauber.exemplolivro.features.auth.AuthContract
import br.com.nglauber.exemplolivro.features.auth.AuthPresenter
import br.com.nglauber.exemplolivro.features.login.LoginActivity

open class BaseFragment : Fragment(), AuthContract.View {

    var mAuthPresenter : AuthContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuthPresenter = AuthPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        mAuthPresenter?.subscribe()
    }

    override fun onPause() {
        super.onPause()
        mAuthPresenter?.unsubscribe()
    }


    override fun setPresenter(presenter: AuthContract.Presenter) {
        mAuthPresenter = presenter
    }

    override fun logoutView() {
        startActivity(Intent(activity, LoginActivity::class.java))
        activity.finish()
    }
}