package br.com.nglauber.exemplolivro.features.postslist

import br.com.nglauber.exemplolivro.shared.BaseContract
import br.com.nglauber.exemplolivro.shared.binding.PostBinding

interface ListPostsContract {

    interface Presenter : BaseContract.BasePresenter {

        fun addNewPost()

        fun editPost(postId : Long)

        fun loadPosts()
    }

    interface View : BaseContract.BaseView<Presenter> {

        fun addNewPost()

        fun editPost(postId : Long)

        fun showProgress(show : Boolean)

        fun updateList(posts : List<PostBinding>)

        fun showLoadErrorMessage()

        fun showEmptyView(visible : Boolean)
    }
}