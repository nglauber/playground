package br.com.nglauber.marvel.model.api.paging

import android.arch.paging.PageKeyedDataSource
import android.util.Log
import br.com.nglauber.marvel.model.api.MarvelApi
import br.com.nglauber.marvel.model.api.entity.Character
import io.reactivex.disposables.CompositeDisposable

class CharactersDataSource(
        private val marvelApi: MarvelApi,
        private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Character>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Character>) {
        val numberOfItems = params.requestedLoadSize
        createObservable(0, 1, numberOfItems, callback, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page + 1, numberOfItems, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page - 1, numberOfItems, null, callback)
    }

    private fun createObservable(requestedPage: Int,
                                 adjacentPage: Int,
                                 requestedLoadSize: Int,
                                 initialCallback: LoadInitialCallback<Int, Character>?,
                                 callback: LoadCallback<Int, Character>?) {
        compositeDisposable.add(
                marvelApi.allCharacters(requestedPage * requestedLoadSize)
                        .subscribe(
                                { response ->
                                    Log.d("NGVL", "Loading page: $requestedPage")
                                    initialCallback?.onResult(response.data.results, null, adjacentPage)
                                    callback?.onResult(response.data.results, adjacentPage)
                                },
                                { t ->
                                    Log.d("NGVL", "Error loading page: $requestedPage", t)
                                }
                        )
        );
    }
}