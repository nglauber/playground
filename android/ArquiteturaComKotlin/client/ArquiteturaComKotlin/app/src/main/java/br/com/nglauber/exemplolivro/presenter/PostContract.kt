package br.com.nglauber.exemplolivro.presenter

import br.com.nglauber.exemplolivro.view.binding.PostBinding

interface PostContract {

    interface PostPresenter {

        fun selectImage()

        fun selectLocation()

        fun updateImage(uri: String?)

        fun updateLocation(latitude: Double, longitude: Double)

        fun savePost(postBinding : PostBinding)

        fun deletePost(postBinding : PostBinding)

        fun loadPost(postId: Long)
    }

    interface PostView {

        fun selectImage()

        fun selectLocation()

        fun showPost(postBinding: PostBinding)

        fun showImage(uri: String)

        fun showLocation(latitude: Double, longitude: Double)

        fun showSaveMessage(success : Boolean)

        fun showDeleteMessage(success : Boolean)

        fun showLoadingProgress(visible : Boolean)

        fun showSavingProgress(visible : Boolean)

        fun showLoadError()

        fun hideImage()

        fun hideLocation()

        fun close()
    }
}