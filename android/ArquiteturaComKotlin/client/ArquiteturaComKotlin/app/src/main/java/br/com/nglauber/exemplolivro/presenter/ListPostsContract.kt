package br.com.nglauber.exemplolivro.presenter

import br.com.nglauber.exemplolivro.view.binding.PostBinding

interface ListPostsContract {

    interface ListPostsPresenter {

        fun addNewPost()

        fun editPost(postId : Long)

        fun loadPosts()
    }

    interface ListPostsView {

        fun addNewPost()

        fun editPost(postId : Long)

        fun showProgress(show : Boolean)

        fun updateList(posts : List<PostBinding>)

        fun showLoadErrorMessage()

        fun showEmptyView(visible : Boolean)
    }
}