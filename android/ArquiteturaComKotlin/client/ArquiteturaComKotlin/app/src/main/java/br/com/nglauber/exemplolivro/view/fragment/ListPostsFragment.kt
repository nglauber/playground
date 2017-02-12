package br.com.nglauber.exemplolivro.view.fragment


import android.content.Intent
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.nglauber.exemplolivro.R
import br.com.nglauber.exemplolivro.databinding.FragmentListPostsBinding
import br.com.nglauber.exemplolivro.presenter.ListPostsContract
import br.com.nglauber.exemplolivro.presenter.ListPostsPresenterImpl
import br.com.nglauber.exemplolivro.view.activity.PostActivity
import br.com.nglauber.exemplolivro.view.adapter.PostListAdapter
import br.com.nglauber.exemplolivro.view.binding.PostBinding

class ListPostsFragment : Fragment(), ListPostsContract.ListPostsView {

    var mBinding: FragmentListPostsBinding? = null
    var presenter: ListPostsContract.ListPostsPresenter? = null

    init {
        presenter = ListPostsPresenterImpl(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate<FragmentListPostsBinding>(
                inflater, R.layout.fragment_list_posts, container, false)
        mBinding?.presenter = presenter

        mBinding?.postListRecyclerview?.layoutManager =
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                    LinearLayoutManager(activity)
                else
                    GridLayoutManager(activity, 2)

        return mBinding?.root
    }

    override fun onStart() {
        super.onStart()
        presenter?.loadPosts()
    }

    override fun addNewPost() {
        startActivity(Intent(activity, PostActivity::class.java))
    }

    override fun editPost(postId: Long) {
        startActivity(Intent(activity, PostActivity::class.java).putExtra(PostActivity.EXTRA_ID, postId))
    }

    override fun showProgress(show: Boolean) {
        mBinding?.postListSwipe?.isRefreshing = show
    }

    override fun updateList(posts: List<PostBinding>) {
        val adapter = PostListAdapter(posts) {
            presenter?.editPost(it.id)
        }
        mBinding?.postListRecyclerview?.adapter = adapter
    }

    override fun showEmptyView(visible : Boolean) {
        mBinding?.postListEmpty?.emptyRoot?.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun showLoadErrorMessage() {
        Toast.makeText(activity, R.string.error_load_message, Toast.LENGTH_SHORT).show()
    }

}
