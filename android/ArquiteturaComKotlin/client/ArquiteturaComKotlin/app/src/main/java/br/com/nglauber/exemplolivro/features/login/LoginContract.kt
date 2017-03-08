package br.com.nglauber.exemplolivro.features.login

import br.com.nglauber.exemplolivro.model.auth.Authentication

interface LoginContract {

    interface Presenter {

        fun startAuthProcess(auth: Authentication)

        fun handleAuthResponse(requestCode: Int, resultCode: Int, data: Any)

        fun attachView(view : View)
    }

    interface View {

        fun showProgress(show: Boolean)

        fun showLoginError()

        fun showMainScreen()
    }

}