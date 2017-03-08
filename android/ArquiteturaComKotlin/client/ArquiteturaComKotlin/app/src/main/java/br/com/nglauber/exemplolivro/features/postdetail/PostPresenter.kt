package br.com.nglauber.exemplolivro.features.postdetail

import br.com.nglauber.exemplolivro.model.persistence.DataSourceFactory
import br.com.nglauber.exemplolivro.model.persistence.PostDataSource
import br.com.nglauber.exemplolivro.shared.binding.PostBinding
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import timber.log.Timber

class PostPresenter(
        private val view: PostContract.View,
        private val db : PostDataSource = DataSourceFactory.getDefaultPostDataSource()) : PostContract.Presenter {

    private val mSubscriptions = CompositeSubscription()

    init {
        view.setPresenter(this)
    }

    override fun loadPost(postId: Long) {
        view.showLoadingProgress(true)
        mSubscriptions.clear()
        val subscr = db.loadPost(postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ post ->
                    view.showLoadingProgress(false)

                    if (post != null) {
                        view.showPost(PostBinding(post))
                    } else {
                        view.showLoadError()
                        view.close()
                    }
                }, { error ->
                    Timber.e(error)

                    view.showLoadingProgress(false)
                    view.showLoadError()
                    view.close()
                })
        mSubscriptions.add(subscr)
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        mSubscriptions.clear()
    }

    override fun selectImage() {
        view.selectImage()
    }

    override fun selectLocation() {
        view.selectLocation()
    }

    override fun updateImage(uri: String?) {
        if (uri != null) {
            view.showImage(uri)
        } else {
            view.hideImage()
        }
    }

    override fun updateLocation(latitude: Double, longitude: Double) {
        if (latitude != 0.0 && longitude != 0.0) {
            view.showLocation(latitude, longitude)
        } else {
            view.hideLocation()
        }
    }

    override fun savePost(postBinding: PostBinding) {
        view.showSavingProgress(true)
        db.savePost(postBinding.post)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    val success = result != 0L
                    view.showSavingProgress(false)
                    view.showSaveMessage(success)
                    if (success)
                        view.close()
                }, { error ->
                    Timber.e(error)
                    view.showSaveMessage(false)
                    view.close()
                }
            )
    }

    override fun deletePost(postBinding: PostBinding) {
        db.deletePost(postBinding.post)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    view.showDeleteMessage(result)
                    if (result)
                        view.close()
                }, { error ->
                    Timber.e(error)
                    view.showDeleteMessage(false)
                })
    }
}
