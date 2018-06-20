package br.com.nglauber.marvel.model.api.paging

import android.arch.paging.DataSource
import br.com.nglauber.marvel.model.api.MarvelApi
import br.com.nglauber.marvel.model.api.entity.Character
import io.reactivex.disposables.CompositeDisposable

class CharactersDataSourceFactory(
        private val compositeDisposable: CompositeDisposable,
        private val marvelApi: MarvelApi
) : DataSource.Factory<Int, Character>() {

    override fun create(): DataSource<Int, Character> {
        return CharactersDataSource(marvelApi, compositeDisposable)
    }
}