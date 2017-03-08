package br.com.nglauber.exemplolivro

import android.app.Application
import br.com.nglauber.exemplolivro.shared.injection.DaggerPostsComponent
import br.com.nglauber.exemplolivro.shared.injection.PostsComponent
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        initDagger()
    }

    companion object {
        lateinit var instance : App private set
        lateinit var component : PostsComponent private set
    }

    private fun initDagger() {
        component = DaggerPostsComponent
                .builder()
                .build()
    }
}