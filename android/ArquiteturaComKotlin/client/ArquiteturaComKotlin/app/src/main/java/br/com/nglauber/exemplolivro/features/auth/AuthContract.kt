package br.com.nglauber.exemplolivro.features.auth

import br.com.nglauber.exemplolivro.shared.BaseContract

interface AuthContract {

    interface Presenter : BaseContract.BasePresenter<View> {

        fun performLogout()
    }

    interface View : BaseContract.BaseView {

        fun logoutView()
    }
}