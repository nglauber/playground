package br.com.nglauber.exemplolivro.features.login

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast

import br.com.nglauber.exemplolivro.R
import br.com.nglauber.exemplolivro.databinding.ActivityLoginBinding
import br.com.nglauber.exemplolivro.model.auth.facebook.FacebookAuth
import br.com.nglauber.exemplolivro.model.auth.google.GoogleAuth
import br.com.nglauber.exemplolivro.features.postslist.ListPostsActivity
import br.com.nglauber.exemplolivro.features.login.LoginContract
import br.com.nglauber.exemplolivro.features.login.LoginPresenter

class LoginActivity : AppCompatActivity(), LoginContract.LoginView {

    private var mBinding: ActivityLoginBinding? = null
    private var mPresenter: LoginContract.LoginPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        mPresenter = LoginPresenter(this)

        mBinding?.loginGoogleSignIn?.setOnClickListener { mPresenter?.startAuthProcess(GoogleAuth(this@LoginActivity)) }
        mBinding?.loginFacebookSignIn?.setOnClickListener { mPresenter?.startAuthProcess(FacebookAuth(this@LoginActivity)) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        mPresenter?.handleAuthResponse(requestCode, resultCode, data)
    }

    override fun showProgress(show: Boolean) {
        mBinding?.loginProgress?.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showLoginError() {
        Toast.makeText(this, "Fail to login", Toast.LENGTH_SHORT).show()
    }

    override fun showMainScreen() {
        finish()
        startActivity(Intent(this, ListPostsActivity::class.java))
    }
}
