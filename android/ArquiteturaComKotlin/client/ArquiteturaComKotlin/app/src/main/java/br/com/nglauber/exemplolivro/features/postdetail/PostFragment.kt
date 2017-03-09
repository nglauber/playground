package br.com.nglauber.exemplolivro.features.postdetail

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.nglauber.exemplolivro.App
import br.com.nglauber.exemplolivro.R
import br.com.nglauber.exemplolivro.databinding.FragmentPostBinding
import br.com.nglauber.exemplolivro.shared.BaseFragment
import br.com.nglauber.exemplolivro.shared.binding.PostBinding
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import javax.inject.Inject

class PostFragment : BaseFragment(), PostContract.View {

    @Inject lateinit var mPresenter: PostContract.Presenter

    private var mPost : PostBinding? = null
    private var mBinding: FragmentPostBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component.inject(this)
        mPresenter.attachView(this)
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false)
        mBinding?.presenter = mPresenter

        if (mPost == null) {
            if (arguments.getLong(EXTRA_ID) > 0) {
                mPresenter.loadPost(arguments.getLong(EXTRA_ID))
            } else {
                mPost = PostBinding()
            }
        }

        mBinding?.post = mPost
        return mBinding?.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_GALLERY) {
                mPresenter.updateImage(data?.data.toString())

            } else if (requestCode == REQUEST_PLACE) {
                val place = PlaceAutocomplete.getPlace(activity, data)
                val latLng = place.latLng
                mPresenter.updateLocation(latLng.latitude, latLng.longitude)
            }
        }
    }

    override fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    override fun selectLocation() {
        try {
            val typeFilter = AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_NONE)
                    .build()
            val intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .setFilter(typeFilter)
                    .build(activity)
            startActivityForResult(intent, REQUEST_PLACE)

        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }
    }

    override fun showPost(postBinding: PostBinding) {
        mPost = postBinding
        mBinding?.post = mPost
    }

    override fun showImage(uri: String) {
        mBinding?.post?.photoUrl = uri
    }

    override fun showLocation(latitude: Double, longitude: Double) {
        mBinding?.post?.latitude = latitude
        mBinding?.post?.longitude = longitude
    }

    override fun hideImage() {
        mBinding?.post?.photoUrl = null
    }

    override fun hideLocation() {
        mBinding?.post?.latitude = 0.0
        mBinding?.post?.longitude = 0.0
    }

    override fun showSaveMessage(success: Boolean) {
        if (success){
            Toast.makeText(activity, R.string.post_message_save_success, Toast.LENGTH_SHORT).show()
            close()
        } else {
            Toast.makeText(activity, R.string.post_message_save_fail, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showDeleteMessage(success: Boolean) {
        if (success){
            Toast.makeText(activity, R.string.post_message_delete_success, Toast.LENGTH_SHORT).show()
            close()
        } else {
            Toast.makeText(activity, R.string.post_message_delete_fail, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showLoadingProgress(visible: Boolean) {
        mBinding?.postSaving?.postSavingText?.text = getString(R.string.text_loading_post)
        mBinding?.postSaving?.postSavingRoot?.visibility = if (visible) View.VISIBLE else View.GONE

    }

    override fun showSavingProgress(visible: Boolean) {
        mBinding?.postSaving?.postSavingText?.text = getString(R.string.text_saving_post)
        mBinding?.postSaving?.postSavingRoot?.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun showLoadError() {
        Toast.makeText(activity, R.string.error_load_post_message, Toast.LENGTH_SHORT).show()
    }

    override fun close() {
        activity.finish()
    }

    companion object {
        val REQUEST_GALLERY = 1
        val REQUEST_PLACE = 2
        val EXTRA_ID = "postId"

        fun newInstance(postId : Long) : PostFragment {
            val postFragment = PostFragment()
            val args = Bundle()
            args.putLong(EXTRA_ID, postId)
            postFragment.arguments = args
            return postFragment
        }
    }
}