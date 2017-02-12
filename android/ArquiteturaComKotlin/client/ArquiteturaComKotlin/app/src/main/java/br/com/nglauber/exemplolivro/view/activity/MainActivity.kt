package br.com.nglauber.exemplolivro.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import br.com.nglauber.exemplolivro.R
import br.com.nglauber.exemplolivro.model.auth.AccessManager

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_main_logout){
            AccessManager.instance.signout()
        }
        return super.onOptionsItemSelected(item)
    }
}
