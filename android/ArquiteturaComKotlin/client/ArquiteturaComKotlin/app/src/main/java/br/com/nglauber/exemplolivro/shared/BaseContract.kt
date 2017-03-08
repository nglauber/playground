package br.com.nglauber.exemplolivro.shared

interface BaseContract {

    interface BasePresenter<T> {

        fun subscribe()

        fun unsubscribe()

        fun attachView(view: T)
    }

    interface BaseView {

    }
}