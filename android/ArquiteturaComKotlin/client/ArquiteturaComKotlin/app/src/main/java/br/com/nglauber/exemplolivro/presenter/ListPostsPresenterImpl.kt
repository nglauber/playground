package br.com.nglauber.exemplolivro.presenter

import br.com.nglauber.exemplolivro.model.persistence.DataSourceFactory
import br.com.nglauber.exemplolivro.model.persistence.PostDataSource
import br.com.nglauber.exemplolivro.view.binding.PostBinding
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*


class ListPostsPresenterImpl(private val view: ListPostsContract.ListPostsView,
                             private val dataSource: PostDataSource = DataSourceFactory.getDefaultPostDataSource())
        : ListPostsContract.ListPostsPresenter {

    override fun loadPosts() {
        view.showProgress(true)
        dataSource.loadPosts()
                .subscribeOn(Schedulers.io())
                .flatMap { posts ->
                    val postsBindingList = ArrayList<PostBinding>()

                    posts.forEachIndexed ({ i, post ->
                        postsBindingList.add(PostBinding(post))
                    })
                    Observable.just(postsBindingList)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                            postsBindingList ->
                            view.updateList(postsBindingList)
                            view.showProgress(false)
                            view.showEmptyView(postsBindingList.size == 0)
                        }, {
                        throwable ->
                            view.showProgress(false)
                            view.showLoadErrorMessage()
                        }
                )
    }

    override fun editPost(postId: Long) {
        view.editPost(postId)
    }

    override fun addNewPost() {
        view.addNewPost()
    }
}
