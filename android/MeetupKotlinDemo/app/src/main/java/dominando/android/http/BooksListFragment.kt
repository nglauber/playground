package dominando.android.http

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import dominando.android.http.model.Volume
import kotlinx.android.synthetic.main.fragment_books_list.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class BooksListFragment : Fragment(), CoroutineScope, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private val booksList = mutableListOf<Volume>()
    private var adapter: ArrayAdapter<Volume>? = null

    private var searchView: SearchView? = null
    private var lastSearchTerm: String = ""

    private lateinit var job: Job
    private var downloadJob: Job? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_books_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = BookListAdapter(requireContext(), booksList)
        listView.emptyView = txtMessage
        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            startActivity(Intent(requireContext(), BookDetailsActivity::class.java).apply {
                putExtra("book", booksList[position])
            })
        }
        if (booksList.isNotEmpty()) {
            showProgress(false)
        } else {
            startDownload("Dominando o Android")
        }
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            txtMessage.setText(R.string.message_progress)
        }
        txtMessage.visibility = if (show) View.VISIBLE else View.GONE
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun startDownload(q: String) {
        if (downloadJob == null) {
            if (BookHttp.hasConnection(requireContext())) {
                startDownloadJson(q)
            } else {
                progressBar.visibility = View.GONE
                txtMessage.setText(R.string.error_no_connection)
            }
        } else if (downloadJob?.isActive == true) {
            showProgress(true)
        }
    }

    private fun startDownloadJson(q: String) {
        downloadJob = launch {
            showProgress(true)
            val searchResult = withContext(Dispatchers.IO) {
                BookHttp.searchBook(q)
            }
            updateBookList(searchResult?.items)
            showProgress(false)
        }
    }

    private fun updateBookList(result: List<Volume>?) {
        if (result != null) {
            booksList.clear()
            booksList.addAll(result)
        } else {
            txtMessage.setText(R.string.error_load_books)
        }
        adapter?.notifyDataSetChanged()
        downloadJob = null
    }

    override fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, menuInflater)
        menuInflater?.inflate(R.menu.livros_list, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        searchItem?.setOnActionExpandListener(this)
        searchView = searchItem?.actionView as SearchView
        searchView?.queryHint = getString(R.string.action_search)
        searchView?.setOnQueryTextListener(this)
        if (lastSearchTerm.isNotEmpty()) {
            val query = lastSearchTerm
            searchItem.expandActionView()
            searchView?.setQuery(query, false)
            searchView?.clearFocus()
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        val q = query ?: ""
        if (q.length > 3) {
            startDownload(query ?: "")
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onMenuItemActionExpand(item: MenuItem?) = true // para expandir a view

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        lastSearchTerm = ""
        return true
    }
}
