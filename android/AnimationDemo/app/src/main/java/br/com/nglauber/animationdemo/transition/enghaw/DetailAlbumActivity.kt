package br.com.nglauber.animationdemo.transition.enghaw

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import br.com.nglauber.animationdemo.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_album.*
import kotlinx.android.synthetic.main.content_detail_album.*
import org.parceler.Parcels

class DetailAlbumActivity : AppCompatActivity() {

    companion object {
        val EXTRA_DISCO = "disco"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_album)

        val disco = Parcels.unwrap<Album>(intent.getParcelableExtra(EXTRA_DISCO))

        ActivityCompat.postponeEnterTransition(this)
        fillFields(disco)
    }

    private fun fillFields(album: Album) {
        ViewCompat.setTransitionName(imgCapa, "cover")
        ViewCompat.setTransitionName(toolbar, "title")
        ViewCompat.setTransitionName(txtAno, "year")

        loadCover(album)
        txtTitulo.text = album.title
        txtAno.text = album.year.toString()
        txtGravadora.text = album.recordCompany
        toolbar.title = album.title

        var sb = StringBuilder()
        for (integrante in album.formation) {
            if (sb.isNotEmpty()) sb.append('\n')
            sb.append(integrante)
        }
        txtFormacao.text = sb.toString()
        sb = StringBuilder()
        for (i in album.tracks.indices) {
            if (sb.isNotEmpty()) sb.append('\n')
            sb.append(i + 1).append(". ").append(album.tracks[i])
        }
        txtMusicas.text = sb.toString()
    }

    private fun loadCover(album: Album) {
        val picassoTarget = object: com.squareup.picasso.Target {
                override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                    imgCapa.setImageBitmap(bitmap)
                    defineColors(bitmap)
                    ActivityCompat.startPostponedEnterTransition(this@DetailAlbumActivity)
                }

                override fun onBitmapFailed(errorDrawable: Drawable) {
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable) {
                }
        }
        Picasso.with(this)
                .load("file:///android_asset/enghaw/${album.cover}")
                .into(picassoTarget)
    }

    private fun defineColors(bitmap: Bitmap) {
        Palette.from(bitmap).generate { palette ->
            val vibrantColor = palette.getVibrantColor(Color.BLACK)
            val darkVibrantColor = palette.getDarkVibrantColor(Color.BLACK)
            val darkMutedColor = palette.getDarkMutedColor(Color.BLACK)
            val lightMutedColor = palette.getLightMutedColor(Color.WHITE)

            txtTitulo.setTextColor(vibrantColor)
            if (appBar != null) {
                appBar.setBackgroundColor(vibrantColor)
            } else {
                toolbar.setBackgroundColor(Color.TRANSPARENT)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.navigationBarColor = darkMutedColor
                window.statusBarColor = darkMutedColor
            }
            if (toolbarLayout != null) {
                toolbarLayout.setContentScrimColor(darkVibrantColor)
            }
            coordinatorLayout.setBackgroundColor(lightMutedColor)
        }
    }
}
