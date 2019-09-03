package br.com.nglauber.snacksample

import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.setMargins
import androidx.core.view.updateLayoutParams
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private val options = listOf(
        "SnackBar Short",
        "SnackBar Long",
        "SnackBar Indefinite",
        "SnackBar + Anchor",
        "SnackBar colors",
        "SnackBar changing layout",
        "SnackBar Fake",
        "SnackBar Fake Custom"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener { view ->
            Toast.makeText(this, "Hello!", Toast.LENGTH_SHORT).show()
        }

        listView.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            options
        )
        listView.setOnItemClickListener { _, _, i, _ ->
            fab.updateLayoutParams<CoordinatorLayout.LayoutParams> {
                if (i in 0..5) {
                    this.behavior = FloatingActionButton.Behavior()
                } else {
                    this.behavior = FloatingActionButtonBehavior(this@MainActivity, null)
                }
            }
            when (i) {
                0 -> showSnackShort()
                1 -> showSnackLong()
                2 -> showSnackIndefinite()
                3 -> showSnackWithAnchor()
                4 -> showSnackWithColors()
                5 -> showSnackCustomLayoutParams()
                6 -> showSnackFaker()
                7 -> showSnackFakerCustom()
            }
        }
    }

    private fun showSnackShort() {
        Snackbar.make(fab, "Short Snack", Snackbar.LENGTH_SHORT)
            .setAction("Action", null)
            .show()
    }

    private fun showSnackLong() {
        Snackbar.make(fab, "Long Snack", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }

    private fun showSnackIndefinite() {
        Snackbar.make(fab, "Indefinite Snack", Snackbar.LENGTH_INDEFINITE)
            .setAction("Action", null)
            .show()
    }

    private fun showSnackWithAnchor() {
        Snackbar.make(listView, "Snack with Anchor", Snackbar.LENGTH_INDEFINITE)
            .setAction("Action") {
                Toast.makeText(this, "Opa!", Toast.LENGTH_SHORT).show()
            }
            .setAnchorView(fab)
            .show()
    }

    private fun showSnackWithColors() {
        Snackbar.make(listView, "Snack with Colors", Snackbar.LENGTH_LONG)
            .setTextColor(Color.YELLOW)
            .setActionTextColor(Color.GREEN)
            .setBackgroundTint(Color.BLUE)
            .setAction("Action") {
                Toast.makeText(this, "Opa!", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun showSnackCustomLayoutParams() {
        val snack = Snackbar.make(listView, "Snack with Anchor", Snackbar.LENGTH_LONG)
            .setAction("Action") {
                Toast.makeText(this, "Opa!", Toast.LENGTH_SHORT).show()
            }
        snack.view.updateLayoutParams<CoordinatorLayout.LayoutParams> {
            this.setMargins(resources.getDimensionPixelSize(R.dimen.snack_margin))
        }
        snack.show()
    }

    private fun showSnackFaker() {
        FakeSnackBar.make(coordinator, "Custom Snack", "Dismiss") {
            Toast.makeText(this, "Hello!", Toast.LENGTH_SHORT).show()
        }.show()
    }

    private fun showSnackFakerCustom() {
        FakeSnackBar.make(coordinator, R.layout.custom_snack).show()
    }
}
