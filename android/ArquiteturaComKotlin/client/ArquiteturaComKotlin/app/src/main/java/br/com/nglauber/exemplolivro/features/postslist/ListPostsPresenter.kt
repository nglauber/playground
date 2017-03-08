package br.com.nglauber.exemplolivro.features.postslist

import br.com.nglauber.exemplolivro.model.persistence.DataSourceFactory
import br.com.nglauber.exemplolivro.model.persistence.PostDataSource
import br.com.nglauber.exemplolivro.shared.binding.PostBinding
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import timber.log.Timber
import java.util.*


class ListPostsPresenter(private val view: ListPostsContract.View,
                         private val dataSource: PostDataSource = DataSourceFactory.getDefaultPostDataSource())
        : ListPostsContract.Presenter {

    private val mSubscriptions = CompositeSubscription()

    init {
        view.setPresenter(this)
    }

    override fun loadPosts() {
        val postsBindingList = ArrayList<PostBinding>()
        view.showProgress(true)

        mSubscriptions.clear()
        val subscr =
            dataSource.loadPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { post ->
                            postsBindingList.add(PostBinding(post))
                        },
                        { throwable ->
                            Timber.e(throwable)
                            view.showProgress(false)
                            view.showLoadErrorMessage()
                        },
                        {
                            view.updateList(postsBindingList)
                            view.showProgress(false)
                            view.showEmptyView(postsBindingList.size == 0)
                        }
                )
        mSubscriptions.add(subscr)
    }

    override fun subscribe() {
        loadPosts()
    }

    override fun unsubscribe() {
        mSubscriptions.clear()
    }

    override fun editPost(postId: Long) {
        view.editPost(postId)
    }

    override fun addNewPost() {
        view.addNewPost()
    }
}
