package br.com.nglauber.exemplolivro.shared.injection

import br.com.nglauber.exemplolivro.features.login.LoginActivity
import br.com.nglauber.exemplolivro.features.postdetail.PostFragment
import br.com.nglauber.exemplolivro.features.postdetail.PostPresenter
import br.com.nglauber.exemplolivro.features.postslist.ListPostsFragment
import br.com.nglauber.exemplolivro.features.postslist.ListPostsPresenter
import dagger.Component

@Component(modules = arrayOf(PostsModule::class))
interface PostsComponent {
    fun inject(activity : LoginActivity)

    fun inject(presenter : ListPostsPresenter)

    fun inject(fragment : ListPostsFragment)

    fun inject(presenter : PostPresenter)

    fun inject(fragment : PostFragment)
}
