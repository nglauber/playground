package br.com.nglauber.exemplolivro.shared

interface BaseContract {

    interface BasePresenter {

        fun subscribe()

        fun unsubscribe()
    }

    interface BaseView<T> {
        fun setPresenter(presenter: T)
    }
}