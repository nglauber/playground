package br.com.nglauber.exemplolivro.features.postslist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import br.com.nglauber.exemplolivro.R
import br.com.nglauber.exemplolivro.features.auth.AuthContract
import br.com.nglauber.exemplolivro.features.auth.AuthPresenter
import br.com.nglauber.exemplolivro.features.login.LoginActivity
import br.com.nglauber.exemplolivro.shared.BaseActivity

class ListPostsActivity : BaseActivity(), AuthContract.View {

    var mAuthPresenter : AuthContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuthPresenter = AuthPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_main_logout){
            mAuthPresenter?.performLogout()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setPresenter(presenter: AuthContract.Presenter) {
        mAuthPresenter = presenter
    }

    override fun logoutView() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
