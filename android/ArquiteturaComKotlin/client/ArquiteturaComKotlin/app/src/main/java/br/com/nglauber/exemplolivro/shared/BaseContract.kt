package br.com.nglauber.exemplolivro.shared

interface BaseContract {

    interface BasePresenter<in T> {

        fun subscribe()

        fun unsubscribe()

        fun attachView(view: T)
    }

    interface BaseView {
        // Empty
    }
}