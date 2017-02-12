package br.com.nglauber.exemplolivro.presenter

import br.com.nglauber.exemplolivro.model.auth.Authentication

interface LoginContract {

    interface LoginPresenter {

        fun startAuthProcess(auth: Authentication)

        fun handleAuthResponse(requestCode: Int, resultCode: Int, data: Any)
    }

    interface LoginView {

        fun showProgress(show: Boolean)

        fun showLoginError()

        fun goToMainScreen()
    }

}