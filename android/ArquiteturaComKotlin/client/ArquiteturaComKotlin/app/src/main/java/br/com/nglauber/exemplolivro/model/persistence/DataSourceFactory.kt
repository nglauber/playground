package br.com.nglauber.exemplolivro.model.persistence

import br.com.nglauber.exemplolivro.model.persistence.sqlite.PostDb

class DataSourceFactory private  constructor () {

    companion object {
        fun getDefaultPostDataSource() : PostDataSource = PostDb()//PostWeb()
    }
}