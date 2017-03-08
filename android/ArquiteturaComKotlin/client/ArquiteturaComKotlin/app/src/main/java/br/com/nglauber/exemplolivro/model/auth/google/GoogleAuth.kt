package br.com.nglauber.exemplolivro.model.auth.google

import android.content.Intent
import android.support.v4.app.FragmentActivity
import br.com.nglauber.exemplolivro.R
import br.com.nglauber.exemplolivro.model.auth.Authentication
import br.com.nglauber.exemplolivro.model.auth.OnAuthRequestedListener
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import timber.log.Timber

class GoogleAuth(private val mActivity: FragmentActivity) : Authentication {
    private var mAuthListener: OnAuthRequestedListener? = null
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mGoogleSignInOptions: GoogleSignInOptions
    private val mGoogleApiClient: GoogleApiClient
    private val mConnectionFailedListener: GoogleApiClient.OnConnectionFailedListener

    init {
        mConnectionFailedListener = GoogleApiClient.OnConnectionFailedListener { mAuthListener!!.onAuthError() }

        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(mActivity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(mActivity)
                .enableAutoManage(mActivity, mConnectionFailedListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mGoogleSignInOptions)
                .build()
    }

    override fun startAuthProcess(l: OnAuthRequestedListener) {
        mAuthListener = l
        mGoogleApiClient.connect()
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        mActivity.startActivityForResult(signInIntent, Authentication.TYPE_GOOGLE)
    }

    override fun handleAuthResponse(requestCode: Int, resultCode: Int, data: Any) {
        val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data as Intent)
        if (result.isSuccess) {
            val account = result.signInAccount
            if (account != null) {
                firebaseAuthWithGoogle(account)
            }
        } else {
            mAuthListener!!.onAuthError()
        }
        mGoogleApiClient.disconnect()
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Timber.d("firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(mActivity) { task ->
                    Timber.d("signInWithCredential:onComplete:" + task.isSuccessful)

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (task.isSuccessful) {
                        mAuthListener!!.onAuthSuccess()

                    } else {
                        Timber.d("signInWithCredential", task.exception)
                        mAuthListener!!.onAuthError()
                    }
                }
    }
}
