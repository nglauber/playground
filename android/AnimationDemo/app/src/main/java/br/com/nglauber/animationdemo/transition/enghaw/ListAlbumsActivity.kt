package br.com.nglauber.animationdemo.transition.enghaw

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Pair
import br.com.nglauber.animationdemo.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_list_albums.*
import org.parceler.Parcels
import java.io.InputStreamReader


class ListAlbumsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_albums)

        val inputStream = assets.open("enghaw/enghaw.json")
        val gson = Gson()
        val discos = gson.fromJson<Array<Album>>(InputStreamReader(inputStream), Array<Album>::class.java)

        val adapter = DiscoAdapter(this, discos, { view, position, disco ->

            val it = Intent(this, DetailAlbumActivity::class.java)
            it.putExtra(DetailAlbumActivity.EXTRA_DISCO, Parcels.wrap(disco))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val options = ActivityOptions.makeSceneTransitionAnimation(
                        this,
                        Pair(view.findViewById(R.id.imgCapa), "cover"),
                        Pair(view.findViewById(R.id.txtTitulo), "title"),
                        Pair(view.findViewById(R.id.txtAno), "year")
                ).toBundle()

                startActivity(it, options)
            } else {
                startActivity(it)
            }

        })
        val columns = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4
        recyclerView.layoutManager = GridLayoutManager(this, columns)
        recyclerView.adapter = adapter
    }
}
