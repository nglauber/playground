package br.com.nglauber.exemplolivro.shared.injection

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

@Module
class PostsModule {

    @Provides
    fun provideLoginPresenter() : LoginContract.Presenter {
        return LoginPresenter()
    }

    @Provides
    fun providesDataSource(user : User?) : PostDataSource {
        return PostWeb(user?.name)
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