package br.com.nglauber.exemplolivro.shared

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import br.com.nglauber.exemplolivro.features.auth.AuthContract
import br.com.nglauber.exemplolivro.features.login.LoginActivity
import javax.inject.Inject

open class BaseFragment : Fragment(), AuthContract.View {

    @Inject lateinit var mAuthPresenter : AuthContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuthPresenter.attachView(this)
    }

    override fun onResume() {
        super.onResume()
        mAuthPresenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        mAuthPresenter.unsubscribe()
    }

    override fun logoutView() {
        startActivity(Intent(activity, LoginActivity::class.java))
        activity.finish()
        return
    }
}