package br.com.nglauber.exemplolivro.model.auth

import com.google.firebase.auth.FirebaseAuth
import java.lang.ref.WeakReference
import java.util.*

class AccessManager private constructor() {

    private val mCallbacks: MutableMap<AccessChangedListener, WeakReference<FirebaseAuth.AuthStateListener>>
    private val mAuth: FirebaseAuth

    init {
        mAuth = FirebaseAuth.getInstance()
        mCallbacks = HashMap<AccessChangedListener, WeakReference<FirebaseAuth.AuthStateListener>>()
    }

    val currentUser: User?
        get() {
            var appUser: User? = null
            val user = mAuth.currentUser
            if (user != null) {
                appUser = User(
                        user.displayName,
                        user.email,
                        if (user.photoUrl != null) user.photoUrl!!.toString() else null,
                        user.uid)
            }
            return appUser
        }

    fun addAccessChangedListener(accessChangedListener: AccessChangedListener) {
        val auth = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            accessChangedListener.accessChanged(user != null)
        }
        mAuth.addAuthStateListener(auth)
        mCallbacks.put(accessChangedListener, WeakReference(auth))
    }

    fun removeAccessChangedListener(accessChangedListener: AccessChangedListener) {
        val listener = mCallbacks[accessChangedListener]?.get()
        if (listener != null) {
            mAuth.removeAuthStateListener(listener)
            mCallbacks.remove(accessChangedListener)
        }
    }

    fun signout() {
        mAuth.signOut()
    }

    interface AccessChangedListener {
        fun accessChanged(hasAccess: Boolean)
    }

    companion object {
        val instance by lazy { AccessManager() }
    }
}
