package br.com.nglauber.exemplolivro.shared

interface BaseContract {

    interface Presenter<in T> {

        fun subscribe()

        fun unsubscribe()

        fun attachView(view: T)
    }

    interface View {
        // Empty
    }
}