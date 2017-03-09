package br.com.nglauber.exemplolivro.shared.injection

import android.content.Context
import br.com.nglauber.exemplolivro.App
import br.com.nglauber.exemplolivro.features.auth.AuthContract
import br.com.nglauber.exemplolivro.features.auth.AuthPresenter
import br.com.nglauber.exemplolivro.features.login.LoginContract
import br.com.nglauber.exemplolivro.features.login.LoginPresenter
import br.com.nglauber.exemplolivro.features.postdetail.PostContract
import br.com.nglauber.exemplolivro.features.postdetail.PostPresenter
import br.com.nglauber.exemplolivro.features.postslist.ListPostsContract
import br.com.nglauber.exemplolivro.features.postslist.ListPostsPresenter
import br.com.nglauber.exemplolivro.model.auth.AccessManager
import br.com.nglauber.exemplolivro.model.auth.User
import br.com.nglauber.exemplolivro.model.persistence.PostDataSource
import br.com.nglauber.exemplolivro.model.persistence.web.PostWeb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PostsModule(private val application: App) {

    @Provides
    @Singleton
    @ForApplication
    fun provideApplicationContext(): Context {
        return application
    }

    @Provides
    fun provideAccessManager() : AccessManager {
        return AccessManager.instance
    }

    @Provides
    fun provideAuthPresenter() : AuthContract.Presenter {
        return AuthPresenter()
    }

    @Provides
    fun provideLoginPresenter() : LoginContract.Presenter {
        return LoginPresenter()
    }

    @Provides
    fun providesDataSource(user : User?) : PostDataSource {
        return PostWeb(user?.name, application)
    }

    @Provides
    fun provideUser() : User? = AccessManager.instance.currentUser

    @Provides
    fun provideListPostsPresenter() : ListPostsContract.Presenter {
        return ListPostsPresenter()
    }

    @Provides
    fun providePostPresenter() : PostContract.Presenter {
        return PostPresenter()
    }
}