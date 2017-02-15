package br.com.nglauber.exemplolivro.presenter

import br.com.nglauber.exemplolivro.model.persistence.DataSourceFactory
import br.com.nglauber.exemplolivro.model.persistence.PostDataSource
import br.com.nglauber.exemplolivro.view.binding.PostBinding
import java.util.*


class ListPostsPresenterImpl(private val view: ListPostsContract.ListPostsView,
                             private val dataSource: PostDataSource = DataSourceFactory.getDefaultPostDataSource())
        : ListPostsContract.ListPostsPresenter {

    override fun loadPosts() {

        view.showProgress(true)

        try {
            dataSource.loadPosts( { posts ->
                val postsBindingList = ArrayList<PostBinding>()

                posts.forEachIndexed ({ i, post ->
                    postsBindingList.add(PostBinding(post))
                })

                view.showProgress(false)
                view.updateList(postsBindingList)
                view.showEmptyView(postsBindingList.size == 0)
            })

        } catch (e : Exception){
            e.printStackTrace()

            view.showProgress(false)
            view.showLoadErrorMessage()
        }
    }

    override fun editPost(postId: Long) {
        view.editPost(postId)
    }

    override fun addNewPost() {
        view.addNewPost()
    }
}
