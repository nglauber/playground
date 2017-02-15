package br.com.nglauber.exemplolivro.presenter

import br.com.nglauber.exemplolivro.model.persistence.DataSourceFactory
import br.com.nglauber.exemplolivro.model.persistence.PostDataSource
import br.com.nglauber.exemplolivro.view.binding.PostBinding

class PostPresenterImpl(
        private val view: PostContract.PostView,
        private val db : PostDataSource = DataSourceFactory.getDefaultPostDataSource()) : PostContract.PostPresenter {

    override fun loadPost(postId: Long) {
        view.showLoadingProgress(true)
            try {
                db.loadPost(postId, { post ->
                    view.showLoadingProgress(false)

                    if (post != null) {
                        view.showPost(PostBinding(post))
                    } else {
                        view.showLoadError()
                        view.close()
                    }
                })

            } catch (e : Exception) {
                e.printStackTrace();

                view.showLoadingProgress(false)
                view.showLoadError()
                view.close()
            }
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
        try {
            db.savePost(postBinding.post, { result ->
                view.showSavingProgress(false)
                view.showSaveMessage(result)
                if (result)
                    view.close()
            })

        } catch (e :Exception){
            e.printStackTrace()

            view.showSaveMessage(false)
            view.close()
        }
    }

    override fun deletePost(postBinding: PostBinding) {
        db.deletePost(postBinding.post, { result ->
            view.showDeleteMessage(result)
            if (result)
                view.close()
        })
    }
}
