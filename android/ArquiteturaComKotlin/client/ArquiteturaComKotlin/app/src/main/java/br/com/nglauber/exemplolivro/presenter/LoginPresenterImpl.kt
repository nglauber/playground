package br.com.nglauber.exemplolivro.presenter

import br.com.nglauber.exemplolivro.model.auth.Authentication
import br.com.nglauber.exemplolivro.model.auth.OnAuthRequestedListener

class LoginPresenterImpl (private val mLoginView: LoginContract.LoginView) : LoginContract.LoginPresenter {
    private var mAuth: Authentication? = null
    private val mAuthRequested: OnAuthRequestedListener

    init {
        mAuthRequested = object : OnAuthRequestedListener {
            override fun onAuthSuccess() {
                mLoginView.showProgress(false)
                mLoginView.goToMainScreen()
            }

            override fun onAuthError() {
                mLoginView.showProgress(false)
                mLoginView.showLoginError()
            }

            override fun onAuthCancel() {
                mLoginView.showProgress(false)
            }
        }
    }

    override fun startAuthProcess(auth: Authentication) {
        mAuth = auth
        mLoginView.showProgress(true)
        mAuth!!.startAuthProcess(mAuthRequested)
    }

    override fun handleAuthResponse(requestCode: Int, resultCode: Int, data: Any) {
        mAuth!!.handleAuthResponse(requestCode, resultCode, data)
    }
}
