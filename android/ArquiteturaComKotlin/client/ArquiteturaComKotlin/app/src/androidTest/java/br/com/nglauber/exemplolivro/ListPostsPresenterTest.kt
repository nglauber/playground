package br.com.nglauber.exemplolivro

import br.com.nglauber.exemplolivro.model.persistence.PostDataSource
import br.com.nglauber.exemplolivro.features.postslist.ListPostsContract
import br.com.nglauber.exemplolivro.features.postslist.ListPostsPresenter
import br.com.nglauber.exemplolivro.shared.binding.PostBinding
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ListPostsPresenterTest {

    @Rule var mockitoRule = MockitoJUnit.rule()

    var presenter: ListPostsContract.ListPostsPresenter? = null

    @Mock private var view: ListPostsContract.ListPostsView? = null
    @Mock private var dataSource: PostDataSource? = null
    @Mock private val list : List<PostBinding>? = null

    @Before
    fun setup() {
        presenter = ListPostsPresenter(view!!, dataSource!!)
    }

    @Test
    fun testLoadData() {
        presenter?.loadPosts()
        verify(view)?.showProgress(true)
        verify(view)?.updateList(list!!)
    }
}