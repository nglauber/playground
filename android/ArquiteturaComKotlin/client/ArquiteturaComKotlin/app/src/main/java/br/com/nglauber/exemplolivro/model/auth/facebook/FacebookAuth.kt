package br.com.nglauber.exemplolivro.model.auth.facebook

import android.content.ContentValues.TAG
import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.util.Log
import br.com.nglauber.exemplolivro.model.auth.Authentication
import br.com.nglauber.exemplolivro.model.auth.OnAuthRequestedListener
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class FacebookAuth(private val mActivity: FragmentActivity) : Authentication {
    private var mAuthListener: OnAuthRequestedListener? = null
    private val mCallbackManager: CallbackManager
    private val mAuth: FirebaseAuth

    init {
        mAuth = FirebaseAuth.getInstance()

        mCallbackManager = CallbackManager.Factory.create()
    }

    override fun startAuthProcess(l: OnAuthRequestedListener) {
        mAuthListener = l
        val lm = LoginManager.getInstance()
        lm.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult)
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onAuthCancel")
                mAuthListener!!.onAuthCancel()
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
                mAuthListener!!.onAuthError()
            }
        })
        lm.logInWithReadPermissions(mActivity, Arrays.asList("email", "public_profile"))
    }

    override fun handleAuthResponse(requestCode: Int, resultCode: Int, data: Any) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data as Intent)
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:" + token)

        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(mActivity) { task ->
                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful)

                    if (task.isSuccessful) {
                        mAuthListener!!.onAuthSuccess()

                    } else {
                        Log.w(TAG, "signInWithCredential", task.exception)
                        mAuthListener!!.onAuthError()
                    }
                }
    }
}
