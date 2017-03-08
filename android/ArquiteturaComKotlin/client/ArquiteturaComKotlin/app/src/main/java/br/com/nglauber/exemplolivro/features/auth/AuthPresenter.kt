package br.com.nglauber.exemplolivro.features.auth

import br.com.nglauber.exemplolivro.model.auth.AccessManager
import timber.log.Timber

class AuthPresenter(private val view : AuthContract.View) : AuthContract.Presenter {

    private var mAccessManager = AccessManager.instance

    init {
        view.setPresenter(this)
    }

    override fun subscribe() {
        if (mAccessManager.currentUser == null) {
            view.logoutView()
            return
        }
        mAccessManager.addAccessChangedListener(mAuthListener)
    }

    override fun unsubscribe() {
        mAccessManager.removeAccessChangedListener(mAuthListener)
    }

    override fun performLogout() {
        AccessManager.instance.signout()
    }

    private val mAuthListener = object : AccessManager.AccessChangedListener {
        override fun accessChanged(hasAccess: Boolean) {
            Timber.d("AuthPresenter::accessChanged(hasAccess= $hasAccess)")
            if (!hasAccess) {
                view.logoutView()
            }
        }
    }
}