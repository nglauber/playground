package br.com.nglauber.marvel.view.characterslist

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.util.Log

import br.com.nglauber.marvel.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_characters.*

class CharactersActivity : AppCompatActivity() {

    private val viewModel: CharactersViewModel by lazy {
        ViewModelProviders.of(this).get(CharactersViewModel::class.java)
    }

    private val adapter: CharactersAdapter by lazy {
        CharactersAdapter()
    }

    private var recyclerState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)

        val llm = LinearLayoutManager(this)
        recyclerCharacters.layoutManager = llm
        recyclerCharacters.adapter = adapter
        subscribeToList()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable("lmState", recyclerCharacters.layoutManager?.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        recyclerState = savedInstanceState?.getParcelable("lmState")
    }

    private fun subscribeToList() {
        val disposable = viewModel.characterList
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { list ->
                            adapter.submitList(list)
                            if (recyclerState != null) {
                                recyclerCharacters.layoutManager?.onRestoreInstanceState(recyclerState)
                                recyclerState = null
                            }
                        },
                        { e ->
                            Log.e("NGVL", "Error", e)
                        }
                )
    }
}
