package br.com.nglauber.exemplolivro.presenter

import br.com.nglauber.exemplolivro.model.persistence.DataSourceFactory
import br.com.nglauber.exemplolivro.model.persistence.PostDataSource
import br.com.nglauber.exemplolivro.view.binding.PostBinding
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class PostPresenterImpl(
        private val view: PostContract.PostView,
        private val db : PostDataSource = DataSourceFactory.getDefaultPostDataSource()) : PostContract.PostPresenter {

    override fun loadPost(postId: Long) {
        view.showLoadingProgress(true)
        db.loadPost(postId)
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
                    error.printStackTrace()

                    view.showLoadingProgress(false)
                    view.showLoadError()
                    view.close()
                })
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
                    error.printStackTrace()
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
                    error.printStackTrace()
                    view.showDeleteMessage(false)
                })
    }
}
