package br.com.nglauber.exemplolivro.shared.injection

import br.com.nglauber.exemplolivro.features.auth.AuthPresenter
import br.com.nglauber.exemplolivro.features.login.LoginActivity
import br.com.nglauber.exemplolivro.features.postdetail.PostFragment
import br.com.nglauber.exemplolivro.features.postdetail.PostPresenter
import br.com.nglauber.exemplolivro.features.postslist.ListPostsActivity
import br.com.nglauber.exemplolivro.features.postslist.ListPostsFragment
import br.com.nglauber.exemplolivro.features.postslist.ListPostsPresenter
import br.com.nglauber.exemplolivro.shared.BaseFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(PostsModule::class))
interface PostsComponent {

    fun inject(fragment: BaseFragment)

    fun inject(presenter: AuthPresenter)

    fun inject(activity : LoginActivity)

    fun inject(activity: ListPostsActivity)

    fun inject(fragment : ListPostsFragment)

    fun inject(presenter : ListPostsPresenter)

    fun inject(presenter : PostPresenter)

    fun inject(fragment : PostFragment)
}
