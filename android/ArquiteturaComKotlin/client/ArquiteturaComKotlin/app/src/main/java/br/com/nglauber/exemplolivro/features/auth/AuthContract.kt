package br.com.nglauber.exemplolivro.features.auth

interface AuthContract {

    interface Presenter {
        fun subscribe()

        fun unsubscribe()

        fun performLogout()
    }

    interface View {

        fun setPresenter(presenter: Presenter)

        fun logoutView()
    }
}