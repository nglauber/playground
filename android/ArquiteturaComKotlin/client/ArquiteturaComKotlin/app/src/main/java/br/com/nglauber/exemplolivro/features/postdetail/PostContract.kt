package br.com.nglauber.exemplolivro.features.postdetail

import br.com.nglauber.exemplolivro.shared.BaseContract
import br.com.nglauber.exemplolivro.shared.binding.PostBinding

interface PostContract {

    interface Presenter : BaseContract.BasePresenter {

        fun selectImage()

        fun selectLocation()

        fun updateImage(uri: String?)

        fun updateLocation(latitude: Double, longitude: Double)

        fun savePost(postBinding : PostBinding)

        fun deletePost(postBinding : PostBinding)

        fun loadPost(postId: Long)
    }

    interface View : BaseContract.BaseView<Presenter> {

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