package br.com.nglauber.exemplolivro.features.login

import br.com.nglauber.exemplolivro.model.auth.Authentication
import br.com.nglauber.exemplolivro.model.auth.OnAuthRequestedListener

class LoginPresenter : LoginContract.Presenter {

    private var mLoginView: LoginContract.View? = null
    private var mAuth: Authentication? = null
    private val mAuthReqListener: OnAuthRequestedListener

    init {
        mAuthReqListener = object : OnAuthRequestedListener {
            override fun onAuthSuccess() {
                mLoginView?.showProgress(false)
                mLoginView?.showMainScreen()
            }

            override fun onAuthError() {
                mLoginView?.showProgress(false)
                mLoginView?.showLoginError()
            }

            override fun onAuthCancel() {
                mLoginView?.showProgress(false)
            }
        }
    }

    override fun startAuthProcess(auth: Authentication) {
        mAuth = auth
        mLoginView?.showProgress(true)
        mAuth?.startAuthProcess(mAuthReqListener)
    }

    override fun handleAuthResponse(requestCode: Int, resultCode: Int, data: Any) {
        mAuth?.handleAuthResponse(requestCode, resultCode, data)
    }

    override fun attachView(view: LoginContract.View) {
        mLoginView = view
    }
}
